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

        if (viewType == -1) {//if empty

            View v = LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false);

            routeViewHolder = new RouteViewHolder(v);

        } else {

            View v = LayoutInflater.from(context).inflate(R.layout.route_item_format, parent, false);

            routeViewHolder = new RouteViewHolder(v);
        }

        return routeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.RouteViewHolder holder, int position) {

        if(busByRoutes.size()>0) {

            holder.tvDeparture.setText(busByRoutes.get(position).getBusdeparturetime());
            holder.tvArrival.setText(busByRoutes.get(position).getDropingtime());
            holder.tvAvailableSeats.setText("47");
            holder.tvBusDesc.setText(busByRoutes.get(position).getBustype());
            duration = busByRoutes.get(position).getJournyduration();
            holder.tvJourneyHs.setText(duration.substring(1,5)+" HR");
            holder.tvTicketPrice.setText("₹ "+busByRoutes.get(position).getFare());
            holder.busId.setText(busByRoutes.get(position).getBusid());

            holder.itemView.setTag(position);
        }
    }



    @Override
    public int getItemCount() {
        return busByRoutes.size();
    }

     class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.busId)
        TextView busId;

       @BindView(R.id.tvDeparture)
       TextView tvDeparture;

       @BindView(R.id.tvArrival)
       TextView tvArrival;

       @BindView(R.id.tvJourneyHs)
       TextView tvJourneyHs;

       @BindView(R.id.tvBusDesc)
       TextView tvBusDesc;

       @BindView(R.id.tvTicketPrice)
       TextView tvTicketPrice;

       @BindView(R.id.tvAvailableSeats)
       TextView tvAvailableSeats;

         public RouteViewHolder(View itemView) {
             super(itemView);
             ButterKnife.bind(this,itemView);
             itemView.setOnClickListener(this);
         }

         @Override
         public void onClick(View v) {
             if (mItemClickListener != null) {
                 mItemClickListener.onItemClick(v, (Integer) v.getTag());
             }
         }
     }

    @Override
    public int getItemViewType(int position) {

        if (busByRoutes.size() <= 0) {

            return -1;
        }
        return super.getItemViewType(position);
    }

    public void setMItemClickListener(RoutesAdapter.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
