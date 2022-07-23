package br.com.alura.linguagens.api;

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

@RestController
@RequestMapping("/linguagens")
public class LinguagemController {

	@Autowired
	private LinguagemRepository repositorio;

	@GetMapping
	public List<Linguagem> obterListaLinguagens() {
		List<Linguagem> listLinguagensVo = repositorio.findAll().stream()
				.sorted(Comparator.comparing(Linguagem::getRanking))
				.collect(Collectors.toList());;
		return listLinguagensVo;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> obterLinguagemPorId(@PathVariable String id) {
		Optional<Linguagem> linguagemVo = repositorio.findById(id);
		if (!linguagemVo.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
		return ResponseEntity.status(HttpStatus.OK).body(linguagemVo.get());
	}

	@PostMapping
	public Linguagem incluirLinguagem(@RequestBody Linguagem linguagemVo) {
		return repositorio.save(linguagemVo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Linguagem> atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagensVo) {
		Linguagem linguagemAtualizada = repositorio.findById(id).get();
		linguagemAtualizada.setImage(linguagensVo.getImage());
		linguagemAtualizada.setRanking(linguagensVo.getRanking());
		linguagemAtualizada.setTitle(linguagensVo.getTitle());

		repositorio.save(linguagemAtualizada);
		return ResponseEntity.ok(linguagemAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluirLinguagem(@PathVariable("id") String id) {
		Optional<Linguagem> linguagemVo = repositorio.findById(id);
		if (!linguagemVo.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
		repositorio.delete(linguagemVo.get());
		return ResponseEntity.status(HttpStatus.OK).body("Registro excluído com sucesso.");
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<Object> incluirListaLinguagem(@RequestBody List<Linguagem> listaLinguagem) {
		for(Linguagem linguagemVo : listaLinguagem) {
			repositorio.save(linguagemVo);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Registros inclúidos com sucesso.");
	}
	
	@DeleteMapping("/bulk")
	public ResponseEntity<Object> excluirListaLinguagem(@RequestBody List<Linguagem> listaLinguagem) {
		for(Linguagem linguagemVo: listaLinguagem) {
			repositorio.delete(linguagemVo);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Registros excluídos com sucesso.");
	}

}
