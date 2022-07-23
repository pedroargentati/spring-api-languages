package br.com.alura.linguagens.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.alura.linguagens.api.model.LinguagemVo;

public interface LinguagemRepository extends MongoRepository<LinguagemVo, String> {

	
	
}
