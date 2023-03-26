package com.isi.myfinelproject;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public abstract class OnCompletListener {
    public abstract void onComplete(@NonNull Task<AuthResult> task);
}
