package br.com.alura.linguagens.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "principaisLinguagens")
public class LinguagemVo {

	@Id
	private String id;

	private String title;
	private String image;
	private Integer ranking;

	public LinguagemVo(String title, String image, Integer ranking) {
		super();
		this.title = title;
		this.image = image;
		this.ranking = ranking;
	}
	
	public LinguagemVo() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
