package com.example.multicastdnsresolution.Adapters;
/*
import package to perform adapter
* */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.multicastdnsresolution.Constants.Constants;
import com.example.multicastdnsresolution.Model.ScannedList;
import com.example.multicastdnsresolution.R;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class DnsListAdapter extends RecyclerView.Adapter<DnsListAdapter.ViewHolder> {
    private Context mContext;
    private List<ScannedList> scannedLists=new ArrayList<>();
    public DnsListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listdata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScannedList list = scannedLists.get(position);
        holder.txt_serviceName.setText( ""+list.getServiceName());
        holder.txt_serviceType.setText(""+list.getServiceType());
        holder.txt_servicePort.setText(""+list.getPort());
        holder.txt_serviceIp.setText(""+list.getIPAddress());
    }

    @Override
    public int getItemCount() {
        return scannedLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_serviceName;
        TextView txt_serviceType;
        TextView txt_serviceIp;
        TextView txt_servicePort;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_serviceName = itemView.findViewById(R.id.txt_service_name);
            txt_serviceType = itemView.findViewById(R.id.txt_service_type);
            txt_serviceIp = itemView.findViewById(R.id.txt_service_ip);
            txt_servicePort = itemView.findViewById(R.id.txt_service_port);
        }
    }

    public void updateList(ScannedList list) {
        scannedLists.add(list);
        removeDuplicate();
        notifyDataSetChanged();
    }

    private void removeDuplicate() {
        TreeSet<ScannedList> treeList = new TreeSet<>(new Comparator<ScannedList>() {
            @Override
            public int compare(ScannedList o1, ScannedList o2) {
                return o1.getIPAddress().toString().compareTo(o2.getIPAddress().toString());
            }
        });
        treeList.addAll(scannedLists);
        scannedLists.clear();
        scannedLists.addAll(treeList);
    }

    public void refreshAdapter() {
        scannedLists.clear();
        notifyDataSetChanged();
    }

}
