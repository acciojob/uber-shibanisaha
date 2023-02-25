package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> drivers = new ArrayList<>();
		driverRepository2.findAll().forEach(driver -> drivers.add(driver));
		Customer customer =  customerRepository2.findById(customerId).get();

		TripBooking trip = new TripBooking();
		trip.setCustomer(customer);
		trip.setFromLocation(fromLocation);
		trip.setToLocation(toLocation);
		trip.setDistanceInKm(distanceInKm);


		for(Driver d: drivers){
			Cab cab = d.getCab();
			if(cab.isAvailable()==true){
				trip.setDriver(d);
				trip.setStatus(TripStatus.CONFIRMED);
				trip.setBill(10*distanceInKm);

				List<TripBooking> list = customer.getTripBookingList();
				list.add(trip);
				customerRepository2.save(customer);
			}
		}
		if(trip.getDriver()==null){
			throw new Exception("No cab available!");
		}
		return trip;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip = tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.CANCELED);
		Customer customer = trip.getCustomer();
		customerRepository2.save(customer);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip = tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.COMPLETED);
		Customer customer = trip.getCustomer();
		customerRepository2.save(customer);

	}
}
