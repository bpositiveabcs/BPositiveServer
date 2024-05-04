package bpos.server.repository.Interfaces;


import bpos.common.model.Donation;

public interface DonationRepository extends IRepository<Integer, Donation> {
    Iterable<Donation> findByTipDonatie(String tipDonatie);
    Iterable<Donation> findByIdTipDonatie(Integer idTipDonatie);
}
