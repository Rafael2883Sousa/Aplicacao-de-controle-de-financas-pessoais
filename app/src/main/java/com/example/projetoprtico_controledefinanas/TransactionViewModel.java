package com.example.projetoprtico_controledefinanas;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }

    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
}
