package com.example.myapplication;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCodeAdapter extends ArrayAdapter<CodeItem> {
    private List<CodeItem> codeListFull;

    public AutoCompleteCodeAdapter(@NonNull Context context, @NonNull List<CodeItem> codeList) {
        super(context, 0, codeList);
        codeListFull = new ArrayList<>(codeList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return codeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.codes_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_code);

        CodeItem codeItem = getItem(position);

        if (codeItem != null) {
            textViewName.setText(codeItem.getCodeName());
                   }

        return convertView;
    }

    private Filter codeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CodeItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(codeListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CodeItem item : codeListFull) {
                    if (item.getCodeName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CodeItem) resultValue).getCodeName();
        }
    };
}
