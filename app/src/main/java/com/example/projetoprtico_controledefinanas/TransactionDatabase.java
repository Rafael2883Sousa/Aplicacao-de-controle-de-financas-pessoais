package com.example.projetoprtico_controledefinanas;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Transaction.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TransactionDatabase extends RoomDatabase {

    private static TransactionDatabase instance;

    public abstract TransactionDao transactionDao();

    public static synchronized TransactionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transaction_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
