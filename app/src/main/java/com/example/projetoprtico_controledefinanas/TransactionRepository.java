package com.example.projetoprtico_controledefinanas;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private ExecutorService executorService;

    public TransactionRepository(Application application) {
        TransactionDatabase database = TransactionDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Transaction transaction) {
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public void delete(Transaction transaction) {
        executorService.execute(() -> transactionDao.delete(transaction));
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
}
