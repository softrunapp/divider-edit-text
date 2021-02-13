package com.softrunapps.divideredittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.List;

public class DividerEditText extends AppCompatEditText {
    private String lastEditTextValue = "";
    private String dividerValue = "";
    private int dividerLength = 3;

    public DividerEditText(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DividerEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DividerEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        initTextChangeListener();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.DividerEditText, 0, 0);
        try {
            dividerLength = typedArray.getInt(R.styleable.DividerEditText_dividerLength, 0);
            dividerValue = typedArray.getString(R.styleable.DividerEditText_dividerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initTextChangeListener();
    }

    private void initTextChangeListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (dividerLength == 0) {
                    return;
                }
                initDivider();
            }
        });
    }

    private void initDivider() {
        int courser = dividerLength;

        String currentValue = getText().toString()
                .replace(dividerValue, "")
                .replace(" ", "").trim();

        if (lastEditTextValue.equals(currentValue)) {
            return;
        }
        lastEditTextValue = currentValue;
        if (currentValue.length() < courser) {
            return;
        }

        List<String> words = new ArrayList<>();
        for (int i = 0; i < currentValue.length(); i += dividerLength) {
            if (courser > currentValue.length()) {
                words.add(currentValue.substring(i, currentValue.length()));
                break;
            } else {
                words.add(currentValue.substring(i, courser));
            }
            courser += dividerLength;
        }
        StringBuilder result = new StringBuilder(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            result.append(" ");
            result.append(dividerValue);
            result.append(" ");
            result.append(words.get(i));
        }
        post(new Runnable() {
            @Override
            public void run() {
                setSelection(getText().length());
            }
        });
        this.setText(result);

    }

    public int getDividerLength() {
        return dividerLength;
    }

    public void setDividerLength(int dividerLength) {
        this.dividerLength = dividerLength;
    }

    public String getDividerValue() {
        return dividerValue;
    }

    public void setDividerValue(String dividerValue) {
        this.dividerValue = dividerValue;
    }
}
