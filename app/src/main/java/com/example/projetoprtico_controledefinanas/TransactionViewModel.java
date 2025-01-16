package com.example.projetoprtico_controledefinanas;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository; // Repositório para acessar as operações de transação
    private LiveData<List<Transaction>> allTransactions; // Lista de todas as transações

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction); // método de inserção no repositório
    }

    public void delete(Transaction transaction) {
        repository.delete(transaction); // método de exclusão no repositório
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Transaction> getTransactionById(int transactionId) {
        return repository.getTransactionById(transactionId); // Retorna a transação com o ID especificado
    }

}
