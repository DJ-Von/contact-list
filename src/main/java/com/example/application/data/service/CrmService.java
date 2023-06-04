package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository) {
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return contactRepository.findAll();
        } else {
            return contactRepository.search(filterText);
        }
    }

    public long countContacts(){
        return contactRepository.count();
    }

    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact){
        if(contact == null){
            System.err.println("Contact is null");
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public List<Company> findAllCompanies(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return companyRepository.findAll();
        } else {
            return companyRepository.search(filterText);
        }
    }

    public void deleteCompany(Company company){
        companyRepository.delete(company);
    }

    public void saveCompany(Company company){
        if(company == null){
            System.err.println("Company is null");
        }
        companyRepository.save(company);
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public List<Status> findAllStatuses(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return statusRepository.findAll();
        } else {
            return statusRepository.search(filterText);
        }
    }

    public void deleteStatus(Status status){
        statusRepository.delete(status);
    }

    public void saveStatus(Status status){
        if(status == null){
            System.err.println("Status is null");
        }
        statusRepository.save(status);
    }
}
