package br.com.leandro.prova_a3;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LocalViewHolder extends RecyclerView.ViewHolder {
    TextView TextViewTitulo;
    TextView TextViewLatitude;
    TextView TextViewLongitude;
    TextView TextViewCadastro;

    LocalViewHolder(View raiz){

        super(raiz);

        this.TextViewTitulo = raiz.findViewById(R.id.TextViewTitulo);
        this.TextViewLatitude = raiz.findViewById(R.id.TextViewLatitude);
        this.TextViewLongitude = raiz.findViewById(R.id.TextViewLongitude);
        this.TextViewCadastro = raiz.findViewById(R.id.TextViewCadastro);

        raiz.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mLongClickListener.onItemLongClick(v, getAdapterPosition());
                return false;
            }

        });

    }

    public LocalViewHolder.LongClickListener mLongClickListener;

    public interface LongClickListener{
        void onItemLongClick(View v, int position);
    }

    public void setOnLongClickListener(LocalViewHolder.LongClickListener longClickListener){
        mLongClickListener = longClickListener;
    }

}