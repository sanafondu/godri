package godriwaladarshan.xyz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;


public class CardAdapterMain extends RecyclerView.Adapter<CardAdapterMain.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<SuperHero> superHeroes;

    //Constructor of this class
    public CardAdapterMain(List<SuperHero> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.superheroes_list_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        SuperHero superHero =  superHeroes.get(position);


        //Showing data on the views

        holder.textViewName.setText(superHero.getName());
        holder.textViewPublisher.setText(superHero.getPublisher());

    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views

        public TextView textViewName;
        public TextView textViewPublisher;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);
        }
    }

}