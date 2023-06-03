package application;

public class Voiture {
	private static int id=0;
	private String matricule;
	private String marque;
	private String modele;
	private String annee;
	

	public Voiture(String marque, String modele, String annee,String matricule) {
		id++;
		this.marque = marque;
		this.modele = modele;
		this.annee = annee;
		this.matricule=matricule;
	}
	
	public static int getId() {
		return id;
	}
	public void setId() {
	   Voiture.id++;
	}
	
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	
	public String getModele() {
		return modele;
	}
	public void setModele(String Modele) {
		this.modele = modele;
	}
	
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String id) {
		this.annee = annee;
	}
	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
}
