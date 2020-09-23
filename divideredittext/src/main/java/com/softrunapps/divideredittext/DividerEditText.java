package com.softrunapps.divideredittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

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
                String currentValue = getText().toString()
                        .replace(dividerValue, "")
                        .replace(" ", "").trim();

                if (lastEditTextValue.equals(currentValue)) {
                    return;
                }
                lastEditTextValue = currentValue;
                String[] stringValueArray = currentValue.split("");
                if (stringValueArray.length == 0) {
                    setText("");
                    return;
                }
                if (stringValueArray.length == 1) {
                    setText(stringValueArray[0]);
                    return;
                }
                StringBuilder resultValue = new StringBuilder(stringValueArray[0]);
                for (int i = 1; i < stringValueArray.length; i++) {
                    resultValue.append(stringValueArray[i]);
                    if (i % dividerLength == 0 && i < stringValueArray.length - 1
                            && !dividerValue.isEmpty()) {
                        resultValue.append(" ");
                        resultValue.append(dividerValue);
                        resultValue.append(" ");
                    }
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        setSelection(getText().length());
                    }
                });
                setText(resultValue.toString());
            }
        });
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
