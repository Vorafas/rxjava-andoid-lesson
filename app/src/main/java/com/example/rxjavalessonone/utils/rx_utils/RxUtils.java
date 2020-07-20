package com.example.rxjavalessonone.utils.rx_utils;

import android.util.Log;
import android.util.Pair;
import android.widget.EditText;

import com.example.rxjavalessonone.model.User;
import com.example.rxjavalessonone.model.UserEntity;
import com.example.rxjavalessonone.utils.validation_utils.IValidator;
import com.example.rxjavalessonone.utils.validation_utils.ValidatorImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxUtils implements IRxUtils {
    private static final String TAG = "RxUtils";

    private IValidator mValidator;

    private Observable<String> mObservableJustOperator;
    private Observable<String> mObservableErrorOperator;
    private Observable<Boolean> mObservableMapOperator;
    private Observable<List<UserEntity>> mObservableFlatMapOperator;
    private Observable<List<UserEntity>> mObservableSwitchMapOperator;
    private Observable<List<User>> mObservableConcatMapOperator;
    private Observable<Boolean> mObservableCombineLatestOperator;

    public RxUtils() {
        mValidator = new ValidatorImpl();
    }

    public Observable<String> introductionJustOperator(String string) {
        mObservableJustOperator = Observable.just(string);
        return mObservableJustOperator;
    }

    public Observable<String> introductionErrorOperator(Throwable e) {
        mObservableErrorOperator = Observable.error(new Exception(e.getMessage()));
        return mObservableErrorOperator;
    }

    public Observable<Boolean> introductionMapOperator(String stringFoMap) {
        mObservableMapOperator = Observable
                .just(stringFoMap)
                .map(string -> string.length() > 0);
        return mObservableMapOperator;
    }

    public Observable<List<UserEntity>> introductionFlatMapOperator(List<User> list) {
        mObservableFlatMapOperator = Observable
                .just(list)
                .flatMap(items -> Observable.just(getSaveListIntoDatabase(items)));
        return mObservableFlatMapOperator;
    }

    public Observable<List<UserEntity>> introductionSwitchMapOperator(EditText editText) {
        mObservableSwitchMapOperator = Observable
                .just(editText)
                .switchMap(query -> Observable.just(getSortedListUser(query)));
        return mObservableSwitchMapOperator;
    }

    public Observable<List<User>> introductionConcatMapOperator(List<User> list) {
        mObservableConcatMapOperator = Observable
                .fromIterable(list)
                .concatMap(item -> Observable.just(updateUserName("Person - ", item)))
                .toList()
                .toObservable();
        return mObservableConcatMapOperator;
    }

    public void introductionSubscribeOperator() {
        mObservableJustOperator.subscribe(string -> {
            Log.d(TAG, "onNext: " + string);
            // TODO some with string inside onNext(String string)
        });

        mObservableErrorOperator.subscribe(error -> {
            // onNext don't called
        }, throwable -> {
            Log.d(TAG, "onError: " + throwable.getMessage());
            // Todo some with error inside onError
        });

        mObservableMapOperator.subscribe(s -> {
            Log.d(TAG, "onError: " + s);
            // TODO some with boolean
        }, throwable -> {
            // onError don't called if error don't occurred
        }, () -> {
            Log.d(TAG, "onCompleted");
            // onCompleted called when observable will completed
        });

        mObservableFlatMapOperator.subscribe(items -> {
            // TODO some with collection items
        }, throwable -> {
            // onError don't called if error don't occurred
        }, () -> {
            // onCompleted called when observable will completed
        });

        mObservableSwitchMapOperator.subscribe(items -> {
            // TODO some with collection items
        }, throwable -> {
            // onError don't called if error don't occurred
        }, () -> {
            // onCompleted called when observable will completed
        });

        mObservableConcatMapOperator.subscribe(items -> {
            // TODO some with collection items
        }, throwable -> {
            // onError don't called if error don't occurred
        }, () -> {
            // onCompleted called when observable will completed
        });
    }

    public void introductionZipOperator(Observable<User> dataFromNetwork, Observable<UserEntity> dataFromDatabase) {
        Observable.zip(dataFromNetwork, dataFromDatabase, Pair::new)
                .subscribe(pair -> {
                    // pair.component1() = TODO some with collection from network;
                    // pair.component2() = TODO some with collection from network;
                }, throwable -> {
                    // TODO handel error
                }, () -> {
                    // TODO handel completed
                });
    }

    public void introductionMergeOperator(Observable<User> dataFromNetwork, Observable<User> dataFromDatabase) {
        Observable
                .merge(dataFromNetwork, dataFromDatabase)
                .subscribe(items -> {
                    // items = TODO some with merged collection from network and database;
                }, throwable -> {
                    // TODO handle error
                }, () -> {
                    // TODO handle complete
                });
    }

    public void introductionCombineLatestOperator(EditText editTextLogin, EditText editTextPassword) {
        Observable<Boolean> isLoginValid = Observable
                .just(editTextLogin)
                .map(editText -> mValidator.isLoginValid(editText.getText().toString()));

        Observable<Boolean> isPasswordValid = Observable
                .just(editTextPassword)
                .map(editText -> mValidator.isPasswordValid(editText.getText().toString()));

        mObservableCombineLatestOperator = Observable.combineLatest(isLoginValid, isPasswordValid, this::isDataValid);
        mObservableCombineLatestOperator.subscribe(is -> {
            // TODO some with result boolean
        });
    }

    public void introductionSubscribeOnWithObserveOnOperators() {
        mObservableCombineLatestOperator
                .subscribeOn(Schedulers.io()) // create new thread inside Observable
                .observeOn(AndroidSchedulers.mainThread()) // result handling of Observable will be returned into main thread
                .subscribe(is -> {
                    // TODO some with result boolean
                });
    }

    private List<UserEntity> getSaveListIntoDatabase(List<User> list) {
        List<UserEntity> convertedList = new ArrayList<UserEntity>();
        // TODO save data and query data after save
        return convertedList;
    }

    private List<UserEntity> getSortedListUser(EditText query) {
        List<UserEntity> sortedList = new ArrayList<UserEntity>();
        return sortedList;
    }

    private User updateUserName(String prefix, User item) {
        item.setFirstName(prefix + item.getFirstName());
        return item;
    }

    private boolean isDataValid(boolean isLoginValid, boolean isPasswordValid) {
        return isLoginValid && isPasswordValid;
    }

    public void introductionPublishSubject() {
        PublishSubject<Integer> source = PublishSubject.create();

        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();
    }

    public void introductionReplaySubject() {
        ReplaySubject<Integer> source = ReplaySubject.create();

        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        source.subscribe(getSecondObserver());
    }

    public void introductionBehaviorSubject() {
        BehaviorSubject<Integer> source = BehaviorSubject.create();

        source.subscribe();
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();
    }

    public void introductionAsyncSubject() {
        AsyncSubject<Integer> source = AsyncSubject.create();

        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();
    }

    public Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "First onSubscribe:" + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "First onNext value: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "First onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "First onCompleted");
            }
        };
    }

    public Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Second onSubscribe:" + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "Second onNext value: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Second onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Second onCompleted");
            }
        };
    }
}
