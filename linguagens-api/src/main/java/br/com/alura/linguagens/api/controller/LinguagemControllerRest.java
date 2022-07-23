package br.com.alura.linguagens.api.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.linguagens.api.LinguagemRepository;
import br.com.alura.linguagens.api.model.LinguagemVo;

@RestController
@RequestMapping("/linguagens")
public class LinguagemControllerRest {

	@Autowired
	private LinguagemRepository repositorio;

	@GetMapping
	public List<LinguagemVo> obterListaLinguagens() {
		List<LinguagemVo> listLinguagensVo = repositorio.findAll().stream()
				.sorted(Comparator.comparing(LinguagemVo::getRanking)).collect(Collectors.toList());
		;
		return listLinguagensVo;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> obterLinguagemPorId(@PathVariable String id) {
		Optional<LinguagemVo> linguagemVo = repositorio.findById(id);
		if (!linguagemVo.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
		return ResponseEntity.status(HttpStatus.OK).body(linguagemVo.get());
	}

	@PostMapping
	public LinguagemVo incluirLinguagem(@RequestBody LinguagemVo linguagemVo) {
		return repositorio.save(linguagemVo);
	}

	@PostMapping("/bulk")
	public ResponseEntity<Object> incluirListaLinguagem(@RequestBody List<LinguagemVo> listaLinguagem) {
		for (LinguagemVo linguagemVo : listaLinguagem) {
			repositorio.save(linguagemVo);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Registros inclúidos com sucesso.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<LinguagemVo> atualizarLinguagem(@PathVariable String id, @RequestBody LinguagemVo linguagensVo) {
		LinguagemVo linguagemAtualizada = repositorio.findById(id).get();
		linguagemAtualizada.setImage(linguagensVo.getImage());
		linguagemAtualizada.setRanking(linguagensVo.getRanking());
		linguagemAtualizada.setTitle(linguagensVo.getTitle());

		repositorio.save(linguagemAtualizada);
		return ResponseEntity.ok(linguagemAtualizada);
	}

	@DeleteMapping("/")
	public ResponseEntity<Object> excluirLinguagem(@RequestBody LinguagemVo linguagemVo) {
		repositorio.delete(linguagemVo);
		return ResponseEntity.status(HttpStatus.OK).body("Registro excluído com sucesso.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluirLinguagem(@PathVariable("id") String id) {
		Optional<LinguagemVo> linguagemVo = repositorio.findById(id);
		if (!linguagemVo.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
		repositorio.delete(linguagemVo.get());
		return ResponseEntity.status(HttpStatus.OK).body("Registro excluído com sucesso.");
	}

	@DeleteMapping("/bulk")
	public ResponseEntity<Object> excluirListaLinguagem(@RequestBody List<LinguagemVo> listaLinguagem) {
		for (LinguagemVo linguagemVo : listaLinguagem) {
			repositorio.delete(linguagemVo);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Registros excluídos com sucesso.");
	}

}
