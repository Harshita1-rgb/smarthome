package com.example.smarthome;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private final List<Device> devices;
    private final DeviceClickListener clickListener;

    public interface DeviceClickListener {
        void onDeviceClick(Device device);
    }

    public DeviceAdapter(List<Device> devices, DeviceClickListener clickListener) {
        this.devices = devices;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_card, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.deviceName.setText(device.getName());
        holder.toggleButton.setText(device.isOn() ? "Turn Off" : "Turn On");

        holder.toggleButton.setBackgroundColor(device.isOn() ? Color.GREEN : Color.RED);

        holder.toggleButton.setOnClickListener(v -> {
            clickListener.onDeviceClick(device);
            Toast.makeText(v.getContext(), device.isOn() ? "Turning Off" : "Turning On", Toast.LENGTH_SHORT).show();
            holder.toggleButton.animate().alpha(0.5f).setDuration(100).withEndAction(() ->
                    holder.toggleButton.animate().alpha(1f).setDuration(100).start());
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        Button toggleButton;

        DeviceViewHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.deviceName);
            toggleButton = itemView.findViewById(R.id.toggleButton);
        }
    }
}
