package bpos.server.service.Interface;

import bpos.common.model.Address;
import bpos.server.service.ServicesExceptions;

import java.util.Optional;

public interface IAddressService {
    Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions;
    Iterable<Address> findAllAddresses() throws ServicesExceptions;
    Optional<Address> saveAddress(Address entity) throws ServicesExceptions;
    Optional<Address> deleteAddress(Address entity) throws ServicesExceptions;
    Optional<Address> updateAddress(Address entity) throws ServicesExceptions;
}
