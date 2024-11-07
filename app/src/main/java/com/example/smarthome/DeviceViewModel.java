package com.example.smarthome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeviceViewModel extends ViewModel {
    private final MutableLiveData<List<Device>> devices;

    public DeviceViewModel() {
        devices = new MutableLiveData<>();
        loadDevices();
    }

    private void loadDevices() {
        List<Device> initialDevices = new ArrayList<>();
        initialDevices.add(new Device("Smart Speaker (Amazon Echo)", false));
        initialDevices.add(new Device("Smart Thermostat (Nest)", true));
        initialDevices.add(new Device("Smart Light (Philips Hue)", false));
        initialDevices.add(new Device("Smart Lock (August)", true));
        devices.setValue(initialDevices);
    }

    public LiveData<List<Device>> getDevices() {
        return devices;
    }

    public void toggleDevice(Device device) {
        List<Device> updatedDevices = new ArrayList<>(devices.getValue());
        for (Device d : updatedDevices) {
            if (d.getName().equals(device.getName())) {
                d.setOn(!d.isOn()); // Toggle status
                break;
            }
        }
        devices.setValue(updatedDevices);
    }

    // Method to add a new device
    public void addDevice(Device device) {
        List<Device> updatedDevices = new ArrayList<>(devices.getValue());
        updatedDevices.add(device);
        devices.setValue(updatedDevices);
    }
}
