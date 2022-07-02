package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GradesActivity extends AppCompatActivity
{
    private OAuth10aService service;
    private OAuth1AccessToken accessToken;

    private JSONObject JSONTermsData;
    private JSONObject JSONGradesData;
    private JSONObject JSONCoursesData;
    private List<String> termsIDs;

    List<CoursesGradesModel> coursesGradesModelList;

    private ArrayList<GradesParentModel> gradesParentModels = new ArrayList<>();
    private ArrayList<GradesChildModel> gradesChildModels = new ArrayList<>();

    private List<List<GradesChildModel>> gradesChildModelsList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        service = OAuthServiceSingleton.getInstance().getService();
        accessToken = OAuthServiceSingleton.getInstance().getAccessToken();

        recyclerView = findViewById(R.id.parentGradesRecycler);

        coursesGradesModelList = new ArrayList<CoursesGradesModel>();

        GetTermsData getTermsData = new GetTermsData();
        getTermsData.execute();
    }

    class GetTermsData extends AsyncTask<Void, Void, Void>
    {
        Response response = null;

        @Override
        protected Void doInBackground(Void... voids)
        {
            final OAuthRequest request = new OAuthRequest(Verb.GET, "https://usosapps.prz.edu.pl/services/courses/user?fields=terms");
            service.signRequest(accessToken, request);

            try
            {
                response = service.execute(request);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            super.onPostExecute(unused);
            try
            {
                JSONTermsData = new JSONObject(response.getBody());

                JSONArray array = JSONTermsData.getJSONArray("terms");
                termsIDs = new ArrayList<>(array.length());

                for (int i = 0; i < array.length(); i++)
                {
                    termsIDs.add(array.getJSONObject(i).get("id").toString());
                }

                GetGradesData getGradesData = new GetGradesData();
                getGradesData.execute();
            } catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    class GetGradesData extends AsyncTask<Void, Void, Void>
    {
        Response response = null;
        String url = "https://usosapps.prz.edu.pl/services/grades/terms?fields=value_symbol&term_ids=";

        @Override
        protected Void doInBackground(Void... voids)
        {
            url = url + termsIDs.get(0);

            for (int i = 1; i < termsIDs.size(); i++)
            {
                url = url + "|" + termsIDs.get(i);
            }

            final OAuthRequest request = new OAuthRequest(Verb.GET, url);
            service.signRequest(accessToken, request);

            try
            {
                response = service.execute(request);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            super.onPostExecute(unused);

            try
            {
                JSONGradesData = new JSONObject(response.getBody());

                for (int i = 0; i < termsIDs.size(); i++)
                {
                    String termID = JSONGradesData.names().get(i).toString();
                    JSONObject termObj = JSONGradesData.getJSONObject(termID);

                    int coursesCounter = termObj.names().length();

                    for (int j = 0; j < coursesCounter; j++)
                    {
                        String courseName = termObj.names().get(j).toString();
                        JSONObject courseObj = termObj.getJSONObject(courseName);

                        JSONObject courseGradesObj = courseObj.getJSONObject("course_grades");

                        String grade = "brak";
                        JSONObject firstObj;

                        if (courseGradesObj.isNull("1"))
                        {
                            grade = "brak";
                        }
                        else
                        {
                            firstObj = courseGradesObj.getJSONObject("1");
                            grade = firstObj.getString("value_symbol");
                        }

                        coursesGradesModelList.add(new CoursesGradesModel(i+1, courseName, grade));
                    }
                }

                GetCoursesNames getCoursesNames = new GetCoursesNames();
                getCoursesNames.execute();

            } catch (JSONException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    class GetCoursesNames extends AsyncTask<Void, Void, Void>
    {
        Response response = null;
        String url = "https://usosapps.prz.edu.pl/services/courses/courses?fields=name&course_ids=";

        @Override
        protected Void doInBackground(Void... voids)
        {
            url = url + coursesGradesModelList.get(0).getCourseName();

            for (int i = 1; i < coursesGradesModelList.size(); i++)
            {
                url = url + "|" + coursesGradesModelList.get(i).getCourseName();
            }

            final OAuthRequest request = new OAuthRequest(Verb.GET, url);
            service.signRequest(accessToken, request);

            try
            {
                response = service.execute(request);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            super.onPostExecute(unused);

            try
            {
                JSONCoursesData = new JSONObject(response.getBody());

                for (int i = 0; i < coursesGradesModelList.size()-1; i++)
                {
                    String courseNameStr = JSONCoursesData.names().get(i).toString();

                    JSONObject course = JSONCoursesData.getJSONObject(courseNameStr);
                    JSONObject name = course.getJSONObject("name");
                    String courseNamePl = name.getString("pl");

                    for (CoursesGradesModel item : coursesGradesModelList)
                    {
                        if (item.getCourseName().equals(courseNameStr))
                        {
                            item.setCourseName(courseNamePl);
                            break;
                        }
                    }
                }

                int counterSemester = 1;

                for (int k = 0; k < coursesGradesModelList.size(); k++)
                {
                    CoursesGradesModel model = coursesGradesModelList.get(k);
                    if (model.getSemester() == counterSemester)
                    {
                        addGradeChildren(model.getCourseName(), model.getGrade());
                    }
                    else
                    {
                        k--;
                        counterSemester++;
                        gradesChildModelsList.add(new ArrayList<GradesChildModel>(gradesChildModels));
                        gradesChildModels.clear();
                    }
                }

                gradesChildModelsList.add(new ArrayList<GradesChildModel>(gradesChildModels));
                gradesChildModels.clear();

                addGradeParent();

                GradesRecycleViewParentAdapter adapter = new GradesRecycleViewParentAdapter(gradesParentModels);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(GradesActivity.this));

            } catch (JSONException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addGradeChildren(String activity, String grade)
    {
        gradesChildModels.add(new GradesChildModel(activity, grade));
    }

    private void addGradeParent()
    {
        String semester = "";
        int number = 0;

        for (int i = 0; i < gradesChildModelsList.size(); i++)
        {
            number = i+1;
            semester = getResources().getString(R.string.semester) + " " + number;
            gradesParentModels.add(new GradesParentModel(semester, gradesChildModelsList.get(i)));
        }
    }
}