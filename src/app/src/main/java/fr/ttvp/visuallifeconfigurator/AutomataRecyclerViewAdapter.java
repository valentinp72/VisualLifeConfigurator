package fr.ttvp.visuallifeconfigurator;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AutomataRecyclerViewAdapter extends RecyclerView.Adapter<AutomataRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;

    public AutomataRecyclerViewAdapter(List<String> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_automata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(mValues.get(position));

        System.out.println("Nous: hello " + mValues.get(position));
        System.out.println("Nous: " + holder.mView.toString());

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Nous: click");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final View mView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView = view.findViewById(R.id.fragment_automata_button);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Nous: hehehe");
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }
}
