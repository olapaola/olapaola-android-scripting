/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.ase.trigger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.google.ase.IntentBuilders;
import com.google.ase.condition.Condition;
import com.google.ase.condition.ConditionConfiguration;

public class ConditionTrigger extends Trigger {
  private static final long serialVersionUID = 5415193311156216064L;
  private final ConditionConfiguration mConditionConfiguration;
  private transient Condition mCondition;

  public ConditionTrigger(String scriptName, TriggerRepository.IdProvider idProvider,
      Service service, ConditionConfiguration conditionConfiguration) {
    super(scriptName, idProvider);
    mConditionConfiguration = conditionConfiguration;
    initializeTransients(service);
  }

  @Override
  public void initializeTransients(final Context context) {
    mCondition = mConditionConfiguration.getCondition(context);

    // TODO: possibly register only one listener.

    mCondition.addBeginListener(new ConditionListener() {
      @Override
      public void run() {
        Intent intent = IntentBuilders.buildStartInBackgroundIntent(getScriptName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // TODO: add extras, check background or foreground
        context.startActivity(intent);
      }
    });

    mCondition.addEndListener(new ConditionListener() {
      @Override
      public void run() {
        Intent intent = IntentBuilders.buildStartInBackgroundIntent(getScriptName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // TODO: add extras, check background or foreground
        context.startActivity(intent);
      }
    });
  }

  @Override
  public void install() {
    mCondition.start();
  }

  @Override
  public void remove() {
    mCondition.stop();
  }
}
