package br.com.leandro.prova_a3.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@IgnoreExtraProperties
public class Local implements Comparable <Local> {

    private Date dataDeCadastro;
    private String descricao;
    private Double latitude;
    private Double longitude;

    public Local(){

    }

    public Local(String descricao, double latitude, double longitude){
        // Atribuindo Data Hora de Agora
        this.setDataDeCadastro(Calendar.getInstance().getTime());

        this.setDescricao(descricao);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Local local) {
        return this.dataDeCadastro.compareTo(local.dataDeCadastro);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dataDeCadastro", dataDeCadastro);
        result.put("descricao", descricao);
        result.put("latitude", latitude);
        result.put("longitude", longitude);

        return result;
    }

}
