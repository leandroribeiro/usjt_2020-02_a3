package br.com.leandro.prova_a3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.leandro.prova_a3.model.Local;
import br.com.leandro.prova_a3.ui.AdicionarActivity;

public class MainActivity extends AppCompatActivity {

    private static List<Local> locais;
    private static CustomAdapter adapter;

    private static CollectionReference locaisReference;

    public static void adicionarLocal(Local novoLocal){
        locais.add(novoLocal);
        locaisReference.add(novoLocal);
        adapter.notifyDataSetChanged();
    }

    public static void removerLocal(int position) {
        locais.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void setupFirebase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        locaisReference = db.collection("locais");
        obterLocais();
    }

    private void obterLocais(){
        locaisReference.addSnapshotListener(new
           EventListener<QuerySnapshot>() {
               @Override
               public void onEvent(@Nullable QuerySnapshot
                                           queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                   locais.clear();
                   for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                       Local localObject = doc.toObject(Local.class);
                       locais.add(localObject);
                   }
                   Collections.sort(locais);
                   adapter.notifyDataSetChanged();
               }
           });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setupFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView cardsLocaisRecyclerView = findViewById(R.id.cardsLocais);

        locais = new ArrayList<Local>();

        adapter = new CustomAdapter(locais, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);

        cardsLocaisRecyclerView.setLayoutManager(linearLayoutManager);
        cardsLocaisRecyclerView.setAdapter(adapter);


        Button adicionar = (Button) findViewById(R.id.buttonAdicionar);
        adicionar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarActivity.class);
                startActivity(intent);
            }
        });
    }
}