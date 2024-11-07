package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DeviceAdapter.DeviceClickListener {

    private RecyclerView recyclerView;
    private DeviceAdapter adapter;
    private DeviceViewModel deviceViewModel;
    private Button addDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addDeviceButton = findViewById(R.id.add_device_button);

        deviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        deviceViewModel.getDevices().observe(this, devices -> {
            adapter = new DeviceAdapter(devices, device -> {
                deviceViewModel.toggleDevice(device);
            });
            recyclerView.setAdapter(adapter);
        });

        addDeviceButton.setOnClickListener(v -> showAddDeviceDialog());
    }

    @Override
    public void onDeviceClick(Device device) {
        Log.d("MainActivity", "Clicked on: " + device.getName());
        deviceViewModel.toggleDevice(device);
        adapter.notifyDataSetChanged(); // Notify the adapter to refresh the view
    }

    // Method to show dialog for adding a new device
    private void showAddDeviceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Device");

        // Set up the input fields
        final EditText deviceNameInput = new EditText(this);
        deviceNameInput.setHint("Device Name");
        builder.setView(deviceNameInput);

        // Set up the buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String deviceName = deviceNameInput.getText().toString();
            if (!deviceName.isEmpty()) {
                deviceViewModel.addDevice(new Device(deviceName, false)); // Default to off
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
