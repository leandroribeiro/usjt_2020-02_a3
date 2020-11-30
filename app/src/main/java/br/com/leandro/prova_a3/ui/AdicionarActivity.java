package br.com.leandro.prova_a3.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.leandro.prova_a3.MainActivity;
import br.com.leandro.prova_a3.R;
import br.com.leandro.prova_a3.model.Local;

public class AdicionarActivity extends AppCompatActivity {


    private EditText editTextDescricao;
    private TextView textViewLatitude;
    private TextView textViewLongitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    CollectionReference locaisReference;

    public static final int REQUEST_CODE = 1001;

    private static final String TAG = "AdicionarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                textViewLatitude.setText(String.format("%f", latitude));
                textViewLongitude.setText(String.format("%f", longitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle
                    extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
            else{
                Toast.makeText(this, getString(R.string.no_gps_no_app), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupFirebase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        locaisReference = db.collection("locais");
    }

    public void completarCadastro(View view) {

        if(validarCampos()) {

            String descricaoText = editTextDescricao.getText().toString();
            String latitudeText = textViewLatitude.getText().toString();
            String longitudeText = textViewLongitude.getText().toString();

            if (descricaoText.isEmpty()) {
                return;
            }

            writeNewPost(descricaoText, latitudeText, longitudeText);
            finish();

        }
    }

    private void writeNewPost(String descricaoText, String latitudeText, String longitudeText) {
        //String key = mDatabase.child("posts").push().getKey();
        Local novoLocal = new Local ( descricaoText, Double.parseDouble(latitudeText), Double.parseDouble(longitudeText));

        Map<String, Object> novaLocalToSave = novoLocal.toMap();

        locaisReference
                .add(novaLocalToSave)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private boolean validarCampos() {

        if(editTextDescricao.getText().toString().trim().length() == 0){
            Toast.makeText(this, "Descrição é obrigatória", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void voltarHome (View view){

        finish();

        //Intent intent = new Intent(AdicionarActivity.this, MainActivity.class);
        //startActivity(intent);

    }
}