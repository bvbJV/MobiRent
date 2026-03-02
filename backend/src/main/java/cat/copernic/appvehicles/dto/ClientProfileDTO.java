package cat.copernic.appvehicles.dto;

import java.time.LocalDate;

public class ClientProfileDTO {

    private String dni;
    private String nomComplet;
    private String email;
    private String telefon;
    private String adreca;
    private String nacionalitat;
    private String numeroTargetaCredit;
    private LocalDate dataCaducitatDni;
    private String tipusCarnetConduir;
    private LocalDate dataCaducitatCarnet;

    // Getters & Setters

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getAdreca() { return adreca; }
    public void setAdreca(String adreca) { this.adreca = adreca; }

    public String getNacionalitat() { return nacionalitat; }
    public void setNacionalitat(String nacionalitat) { this.nacionalitat = nacionalitat; }

    public String getNumeroTargetaCredit() { return numeroTargetaCredit; }
    public void setNumeroTargetaCredit(String numeroTargetaCredit) { this.numeroTargetaCredit = numeroTargetaCredit; }

    public LocalDate getDataCaducitatDni() { return dataCaducitatDni; }
    public void setDataCaducitatDni(LocalDate dataCaducitatDni) { this.dataCaducitatDni = dataCaducitatDni; }

    public String getTipusCarnetConduir() { return tipusCarnetConduir; }
    public void setTipusCarnetConduir(String tipusCarnetConduir) { this.tipusCarnetConduir = tipusCarnetConduir; }

    public LocalDate getDataCaducitatCarnet() { return dataCaducitatCarnet; }
    public void setDataCaducitatCarnet(LocalDate dataCaducitatCarnet) { this.dataCaducitatCarnet = dataCaducitatCarnet; }
}