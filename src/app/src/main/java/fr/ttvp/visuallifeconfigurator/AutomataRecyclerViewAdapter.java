package fr.ttvp.visuallifeconfigurator;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.AutomataLight;
import fr.ttvp.visuallifeconfigurator.model.Persitance;


public class AutomataRecyclerViewAdapter extends RecyclerView.Adapter<AutomataRecyclerViewAdapter.ViewHolder> {

    private final List<AutomataLight> mValues;

    public AutomataRecyclerViewAdapter(List<AutomataLight> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_automata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AutomataLight al = mValues.get(position);

        holder.textView.setText(al.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Automata a = Persitance.getInstance().getAutomata(al);
                System.out.println(a);
                System.out.println(a.getCells().get(0).getCellsToCount());
                System.out.println(a.getCells().get(0).getNeighbours());
                System.out.println(Arrays.toString(a.getCells().get(0).getTransitions()));

            }
        });
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
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }
}
