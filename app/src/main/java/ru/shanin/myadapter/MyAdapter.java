package ru.shanin.myadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends ListAdapter<People, MyViewHolder> {
    private static int count = 0;
    public OnPeopleClickListener peopleClickListener = null;
    public static final int MAX_POOL_SIZE = 10;
    public static final int VIEW_TYPE_PEOPLE_ID_0 = 100;
    public static final int VIEW_TYPE_PEOPLE_ID_1 = 200;
    public static final int VIEW_TYPE_PEOPLE_ID_2 = 300;
    public static final int VIEW_TYPE_PEOPLE_ID_3 = 400;

    public MyAdapter(MyDiffCallback diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        showLog("create card for adapter: " + (++count) + " time(s)");
        int layout;
        switch (viewType) {
            case VIEW_TYPE_PEOPLE_ID_0: layout = R.layout.layout_card_0; break;
            case VIEW_TYPE_PEOPLE_ID_1: layout = R.layout.layout_card_1; break;
            case VIEW_TYPE_PEOPLE_ID_2: layout = R.layout.layout_card_2; break;
            case VIEW_TYPE_PEOPLE_ID_3: layout = R.layout.layout_card_3; break;
            default: throw new RuntimeException("Unknown view type " + viewType);
        }
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        People people = getItem(position);
        switch (people.get_id() % 4) {
            case 1:
                return VIEW_TYPE_PEOPLE_ID_1;
            case 2:
                return VIEW_TYPE_PEOPLE_ID_2;
            case 3:
                return VIEW_TYPE_PEOPLE_ID_3;
            default:
                return VIEW_TYPE_PEOPLE_ID_0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        People people = getItem(position);
        viewHolder.tvFirstName.setText(String.valueOf(people.getPeopleInfo().getFirstName()));
        viewHolder.tvSecondName.setText(people.getPeopleInfo().getSecondName());
        viewHolder.tvKnowledge.setText(people.getPeopleInfo().getListOfKnowledge().toString());
        String mDrawableName = people.getPeopleInfo().getPathToPhoto();
        //TODO  getResources() here
        Context context = viewHolder.itemView.getContext();
        int resID = context.getResources().getIdentifier(
                mDrawableName,
                "drawable",
                context.getPackageName()
        );
        viewHolder.imPhoto.setImageResource(resID);
        viewHolder.itemView.setOnClickListener(
                v -> {
                    Toast.makeText(context, "people position = " + position + "\npeople _id = " + people.get_id(), Toast.LENGTH_SHORT).show();
                    //peopleClickListener.onPeopleClick(people.get_id());
                }
        );
    }

    interface OnPeopleClickListener {
        void onPeopleClick(int peopleId);
    }

    private void showLog(String string) {
        Log.d(this.getClass().getSimpleName(), string);
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvFirstName;
    TextView tvSecondName;
    TextView tvKnowledge;
    ImageView imPhoto;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvFirstName = itemView.findViewById(R.id.tv_fn);
        tvSecondName = itemView.findViewById(R.id.tv_sn);
        tvKnowledge = itemView.findViewById(R.id.tv_knowledge);
        imPhoto = itemView.findViewById(R.id.iv_photo);
    }
}

class MyDiffCallback extends DiffUtil.ItemCallback<People> {

    @Override
    public boolean areItemsTheSame(@NonNull People oldPeople, @NonNull People newPeople) {
        return (oldPeople.get_id()) == (newPeople.get_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull People oldPeople, @NonNull People newPeople) {
        return oldPeople.equals(newPeople);
    }
}