// Copyright 2016 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.android.globalactionbarservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class GlobalActionBarService extends AccessibilityService {

    FrameLayout mLayout;
    TextToSpeech t1;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // Create an overlay and display the action bar
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.action_bar, mLayout);
        wm.addView(mLayout, lp);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
       // prepareForReading();

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        final int eventType = event.getEventType();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked: ";
                break;
       //     case AccessibilityEvent.TYPE_VIEW_FOCUSED:
         //       eventText = "Focused: ";
           //     break;
        }

        if(event.getContentDescription()!=null){
            eventText = eventText + event.getContentDescription();
        }
        else {
            eventText = eventText + event.getText();

        }

        speakToUser(eventText);


    }

    private void speakToUser(String eventText) {
        t1.speak(eventText, TextToSpeech.QUEUE_FLUSH, null,null);

    }

    @Override
    public void onInterrupt() {

    }


    int mDebugDepth;
    AccessibilityNodeInfo mNodeInfo;
    String TAG="Text";


  /*  private void printAllViews(AccessibilityNodeInfo mNodeInfo) {
        if (mNodeInfo == null) return;
        String log ="";
      //  for (int i = 0; i < mDebugDepth; i++) {
         //   log += ".";
       // }
        if(mNodeInfo.getText()!=null || mNodeInfo.getViewIdResourceName()!=null)
            if(mNodeInfo.getViewIdResourceName()!=null){
                t1.speak(mNodeInfo.getViewIdResourceName(), TextToSpeech.QUEUE_FLUSH, null,null);

            }
            if(mNodeInfo.getText()!=null)
                t1.speak(mNodeInfo.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);

        log+="("+mNodeInfo.getText() +" <-- "+
                mNodeInfo.getViewIdResourceName()+")";
        Log.d(TAG, log);
        if (mNodeInfo.getChildCount() < 1) return;
        mDebugDepth++;

        for (int i = 0; i < mNodeInfo.getChildCount(); i++) {
            printAllViews(mNodeInfo.getChild(i));
        }
        mDebugDepth--;
    }*/
}
