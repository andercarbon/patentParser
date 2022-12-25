package com.basf.parser.patentParser.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "PATENT")
public class Patent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String title;
	Date patentDate;
	@Lob
	@Column( length = 100000 )
	String patentAbstract;
	@ElementCollection(targetClass=String.class)
	List<String> chemicalWords;
	
	public Patent() {
		super();
	}

	public Patent(String title, Date patentDate, String patentAbstract, List<String> chemicalWords) {
		super();
		this.title = title;
		this.patentDate = patentDate;
		this.patentAbstract = patentAbstract;
		this.chemicalWords = chemicalWords;
	}
	
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPatentDate() {
		return patentDate;
	}

	public void setPatentDate(Date patentDate) {
		this.patentDate = patentDate;
	}

	public String getPatentAbstract() {
		return patentAbstract;
	}

	public void setPatentAbstract(String patentAbstract) {
		this.patentAbstract = patentAbstract;
	}

	public List<String> getChemicalWords() {
		return chemicalWords;
	}

	public void setChemicalWords(List<String> chemicalWords) {
		this.chemicalWords = chemicalWords;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chemicalWords, patentAbstract, patentDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patent other = (Patent) obj;
		return Objects.equals(patentAbstract, other.patentAbstract) && Objects.equals(patentDate, other.patentDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Patent [title=" + title + ", patentDate=" + patentDate + ", patentAbstract=" + patentAbstract + "]";
	}
}
