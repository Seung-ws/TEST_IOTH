package com.project2.mandara.Data;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.project2.mandara.R;

import java.util.Stack;

import static com.project2.mandara.Data.StaticData.reader;

public class BitmapRecord {
  Stack<Bitmap> stack1=null;
  Stack<Bitmap> stack2=null;
  public BitmapRecord(){
      stack1=new Stack<Bitmap>();
      stack2=new Stack<Bitmap>();
  }
    public void AddBitmapRecord(Bitmap bitmap){

            stack2.clear();
            stack1.push(Bitmap.createBitmap(bitmap));




    }
    public Bitmap forward_getBitmapRecord()
    {
        if(!stack2.isEmpty())
        {
            return stack1.push(Bitmap.createBitmap(stack2.pop()));
        }
        return null;
    }
    public Bitmap back_getBitmapRecord()
    {
        if(!stack1.isEmpty()&& stack1.size()>1)
        {
            stack2.push(Bitmap.createBitmap(stack1.pop()));
            return Bitmap.createBitmap(stack1.peek());
        }

        return null;
    }

    public int getStack1Count(){
      return stack1.size();
    }
    public int getStack2Count(){
      return stack2.size();
    }
    public void Button_state_checking(Context c){

        if(!(reader.getStack1Count()>1))
        {

            ((AppCompatActivity)c).findViewById(R.id.Drawing_btn_Back).setEnabled(false);
        }else
        {

            ((AppCompatActivity)c).findViewById(R.id.Drawing_btn_Back).setEnabled(true);
        }
        if(!(reader.getStack2Count()>0))
        {
            ((AppCompatActivity)c).findViewById(R.id.Drawing_btn_forward).setEnabled(false);
        }
        else
        {
            ((AppCompatActivity)c).findViewById(R.id.Drawing_btn_forward).setEnabled(true);
        }
    }
    public void recordRemove(){
        stack1.clear();
        stack2.clear();
    }


}
