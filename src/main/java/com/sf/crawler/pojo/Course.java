package com.sf.crawler.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2722836869342528434L;

	@JSONField(ordinal=1)
	private Long id;
	
	@JSONField(ordinal=4)
	private String title;
	
	@JSONField(ordinal=6)
	private String introduce;
	
	@JSONField(ordinal=3)
	private String level;
	
	@JSONField(ordinal=2)
	private Integer hot;
	
	@JSONField(ordinal=5)
	private String url;
	
	@JSONField(ordinal=7)
	private String img;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", introduce=" + introduce + ", level=" + level + ", hot="
				+ hot + ", url=" + url + ", img=" + img + "]";
	}

}
