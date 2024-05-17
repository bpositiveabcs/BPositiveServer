package bpos.server.service.Implementation;

import bpos.common.model.Address;
import bpos.server.repository.Interfaces.AddressRepository;
import bpos.server.service.Interface.IAddressService;
import bpos.server.service.ServicesExceptions;

import java.util.Optional;

public class AddressService implements IAddressService {
    private AddressRepository addressRepository;
    @Override
    public Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions {
        return addressRepository.findOne(integer);
    }

    @Override
    public Iterable<Address> findAllAddresses() throws ServicesExceptions {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> saveAddress(Address entity) throws ServicesExceptions {
        return addressRepository.save(entity);
    }

    @Override
    public Optional<Address> deleteAddress(Address entity) throws ServicesExceptions {
        return addressRepository.delete(entity);
    }

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> updateAddress(Address entity) throws ServicesExceptions {
        return addressRepository.update(entity);
    }
}
