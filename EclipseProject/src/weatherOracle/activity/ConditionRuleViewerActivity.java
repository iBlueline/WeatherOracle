// tim is the best

package weatherOracle.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import weatherOracle.filter.*;

public class ConditionRuleViewerActivity extends Activity {

	LayoutParams params;
    LinearLayout mainLayout;
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.condition_rule_viewer_activity);
		mainLayout = (LinearLayout)findViewById(R.id.condition_activity_linear_layout);

		final Button saveButton = (Button) findViewById(R.id.save_filter_button_conditions);
	    
		initializeSaveButtonListener(saveButton);

	//	FilterMenuActivity.conditions = new TreeSet<ConditionRule>();

		CreateAddConditionButton();
		populateConditionRules();
		displayConditionRules();

    }
    
    private void populateConditionRules(){

    	//conditions = FilterMenuActivity.filter.getConditionRules();
    	//conditions = new TreeSet<ConditionRule>();
    	//conditions.add(new ConditionRule("C1", 0, 10));
    }
    
    private void displayConditionRules() {
    	List<ConditionRule> conditionList = new ArrayList<ConditionRule>(FilterMenuActivity.conditions);
    	for (int i = 0; i < conditionList.size(); i++) {    		
    		final RelativeLayout rl = new RelativeLayout(this);
    		//final Button deleteButton = new Button(this);
    		final TextView textview = new TextView(this);
    		int min = conditionList.get(i).getMinMax().first;
    		int max = conditionList.get(i).getMinMax().second;
    		textview.setText(conditionList.get(i).getCondition() + ": " + min + " - " + max);
    		textview.setTextSize(2,15);
         	 
    	  	final Button deleteButton = new Button(this);
          	deleteButton.setText("Delete");
      	
            rl.addView(deleteButton);
    	  	LayoutParams params = (RelativeLayout.LayoutParams)deleteButton.getLayoutParams();
    	  	((android.widget.RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    		
   	  	 	deleteButton.setOnClickListener(new View.OnClickListener() {
   	  	 		public void onClick(View v) {
   	  	 			//DO SOMETHING
   	  	 			mainLayout.removeView(rl); 
   	  	 		}
   	  	 	});
    	  	
    		rl.addView(textview);
    		rl.setBackgroundResource(R.drawable.main_view_element);
    		mainLayout.addView(rl);
    	}
    }
    
    

	private void CreateAddConditionButton() {
    	Button b = (Button)findViewById(R.id.add_condition_filter_button);
    	 b.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view) {
             	Intent myIntent = new Intent(view.getContext(), ConditionAdderActivity.class);
                startActivity(myIntent);     
             } 
         });
    	
    }
    
    
 	@Override
 	public void onWindowFocusChanged(boolean hasFocus){
 	    if(hasFocus) {
 	    	mainLayout.removeAllViews();
 	    	CreateAddConditionButton();
 	    	populateConditionRules();
 	    	displayConditionRules();
 		} else {
 			mainLayout.removeAllViews();
 		}
 	}
 	
 	private void initializeSaveButtonListener(Button saveButton){
		 saveButton.setOnClickListener(new View.OnClickListener()
	        {
	         public void onClick(View v)
	            {
	        	 	
	        	 	// as long as currentFilterName is non empty we know it holds a valid filter name
	        	 	// this fact is guaranteed to us by the checks in the edit text listener ... if 
	        	 	// currentFilterName is the empty string then either (1) the user never specified a
	        	 	// name, or (2) the user tried to specify a name containing only spaces, or (3) the
	        	 	// user entered in a name of a filter that already existed. Either way, the name would
	        	 	// be invalid and the edit text listener would ensure that currentFilterName was not
	        	 	// updated with this invalid name
	        	 	if(!FilterMenuActivity.currentFilterName.equals("")){ 
	        	 		FilterMenuActivity.filter.setName(FilterMenuActivity.currentFilterName);
	        	 		HomeMenuActivity.testFilterList.add(FilterMenuActivity.filter);
	        	 	//	FilterMenuActivity.filterName = "";
	        	 		finish();
	        	 	} else {
	        	 		FilterMenuActivity.tabHost.setCurrentTab(0);
	        	 	}
	            }
	        });
	}
}


