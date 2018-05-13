package b12app.vyom.com.bookmybus.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import b12app.vyom.com.bookmybus.R;
import b12app.vyom.com.bookmybus.model.JBusByRoute;
import b12app.vyom.com.bookmybus.model.JRoutes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private RoutesAdapter.OnItemClickListener mItemClickListener;

    private Context context;

    private List<JBusByRoute.BusinformationBean> busByRoutes;
    private String duration;

    public RoutesAdapter(Context context, List<JBusByRoute.BusinformationBean> busByRoutes) {
        this.context = context;
        this.busByRoutes = busByRoutes;
    }

    @NonNull
    @Override
    public RoutesAdapter.RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RouteViewHolder routeViewHolder;
        View v = LayoutInflater.from(context).inflate(R.layout.route_item_format, parent, false);
        routeViewHolder = new RouteViewHolder(v);
        return routeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.RouteViewHolder holder, int position) {
            holder.tvDeparture.setText(busByRoutes.get(position).getBusdeparturetime());
            holder.tvArrival.setText(busByRoutes.get(position).getDropingtime());
            holder.tvAvailableSeats.setText("47");
            holder.tvBusDesc.setText(busByRoutes.get(position).getBustype());
            duration = busByRoutes.get(position).getJournyduration();
            holder.tvJourneyHs.setText(duration.substring(1, 5) + " HR");
            holder.tvTicketPrice.setText("₹ " + busByRoutes.get(position).getFare());
            holder.busId.setText(busByRoutes.get(position).getBusid());
            holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return busByRoutes.size();
    }

    class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView busId;
        TextView tvDeparture;
        TextView tvArrival;
        TextView tvJourneyHs;
        TextView tvBusDesc;
        TextView tvTicketPrice;
        TextView tvAvailableSeats;

        public RouteViewHolder(View itemView) {
            super(itemView);
            busId = itemView.findViewById(R.id.busId);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvArrival = itemView.findViewById(R.id.tvArrival);
            tvJourneyHs = itemView.findViewById(R.id.tvJourneyHs);
            tvBusDesc = itemView.findViewById(R.id.tvBusDesc);
            tvTicketPrice = itemView.findViewById(R.id.tvTicketPrice);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, (Integer) v.getTag());
            }
        }
    }


    public void setMItemClickListener(RoutesAdapter.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
