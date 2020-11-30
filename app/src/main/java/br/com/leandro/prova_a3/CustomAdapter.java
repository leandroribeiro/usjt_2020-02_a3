package br.com.leandro.prova_a3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leandro.prova_a3.infrastructure.DateHelper;
import br.com.leandro.prova_a3.model.Local;

public class CustomAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private List<Local> localDataSet;
    private Context context;

    public CustomAdapter(List<Local> dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.local_row_item, viewGroup, false);

        return new LocalViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LocalViewHolder viewHolder, final int position) {

        Local model = localDataSet.get(position);

        viewHolder.TextViewTitulo.setText(model.getDescricao());
        viewHolder.TextViewLatitude.setText(String.format("Latitude: %s", model.getLatitude()));
        viewHolder.TextViewLongitude.setText(String.format("Longitude: %s", model.getLongitude()));
        viewHolder.TextViewCadastro.setText(String.format("Data Cadastro: %s", DateHelper.format(model.getDataDeCadastro())));

        viewHolder.setOnLongClickListener(new LocalViewHolder.LongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                MainActivity.removerLocal(position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}