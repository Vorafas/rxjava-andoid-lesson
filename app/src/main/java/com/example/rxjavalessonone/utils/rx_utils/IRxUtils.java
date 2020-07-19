package com.example.rxjavalessonone.utils.rx_utils;

import android.widget.EditText;

import com.example.rxjavalessonone.model.User;
import com.example.rxjavalessonone.model.UserEntity;

import java.util.List;

import rx.Observable;
import rx.Observer;

public interface IRxUtils {

    Observable<String> introductionJustOperator(String string);

    Observable<String> introductionErrorOperator(Throwable e);

    Observable<Boolean> introductionMapOperator(String stringForMap);

    Observable<List<UserEntity>> introductionFlatMapOperator(List<User> list);

    Observable<List<UserEntity>> introductionSwitchMapOperator(EditText editText);

    Observable<List<User>> introductionConcatMapOperator(List<User> list);

    void introductionSubscribeOperator();

    void introductionZipOperator(Observable<User> dataFromNetwork, Observable<UserEntity> dataFromDatabase);

    void introductionMergeOperator(Observable<User> dataFromNetwork, Observable<User> dataFromDatabase);

    void introductionCombineLatestOperator(EditText editTextLogin, EditText editTextPassword);

    void introductionSubscribeOnWithObserveOnOperators(Observable observable);

    void introductionPublishSubject();

    void introductionReplaySubject();

    void introductionBehaviorSubject();

    void introductionAsyncSubject();

    Observer<Integer> getFirsObserver();

    Observer<Integer> getSecondObserver();
}
