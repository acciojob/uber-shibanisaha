package com.driver.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.Cab;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin admin = adminRepository1.findById(adminId).get();
		admin.setPassword(password);
		adminRepository1.save(admin);
		return admin;
	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		adminRepository1.deleteById(adminId);

	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> drivers = new ArrayList<>();
		driverRepository1.findAll().forEach(driver -> {
			Driver driverEntity = new Driver();
			driverEntity.setDriverId(driver.getDriverId());
			driverEntity.setPassword(driver.getPassword());
			driverEntity.setMobile(driver.getMobile());
			driverEntity.setTripBookingList(driver.getTripBookingList());
			Cab cab = new Cab();
			cab.setId(driver.getCab().getId());
			cab.setAvailable(driver.getCab().getAvailable());
			cab.setPerKmRate(driver.getCab().getPerKmRate());
			driverEntity.setCab(cab);
			drivers.add(driverEntity);
		});
		return drivers;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customers = new ArrayList<>();
		customerRepository1.findAll().forEach(customer -> customers.add(customer));
		return customers;
	}

}
