package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.ucsc.dinusha.speedread.sharedPreferenceDB.WebViewTextSharedPreferences;

public class PreLoadTextTemplateActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load_text_template);

        mContext = this;

        CardView mCardViewPreLoadedTextOne = findViewById(R.id.activity_text_sources_preloaded_text_one);
        CardView mCardViewPreLoadedTextTwo = findViewById(R.id.activity_text_sources_preloaded_text_two);
        CardView mCardViewPreLoadedTextThree = findViewById(R.id.activity_text_sources_preloaded_text_three);
        CardView mCardViewPreLoadedTextFour = findViewById(R.id.activity_text_sources_preloaded_text_four);
        CardView mCardViewPreLoadedTextFive = findViewById(R.id.activity_text_sources_preloaded_text_five);
        CardView mCardViewPreLoadedTextSix = findViewById(R.id.activity_text_sources_preloaded_text_six);

        mCardViewPreLoadedTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String mPreLoadedTextOne = "English is the largest language by number of speakers," +
//                        " and the third most-spoken native language in the world, after Standard Chinese and Spanish." +
//                        " It is the most widely learned second language and is either the official language or " +
//                        "one of the official languages in almost 60 sovereign states. " +
//                        "There are more people who have learned it as a second language than there are native speakers." +
//                        " It is estimated that there are over 2 billion speakers of English." +
//                        " English is the most commonly spoken language in the United Kingdom, the United States, Canada, " +
//                        "Australia, Ireland and New Zealand, and it is widely spoken in some areas of the Caribbean, " +
//                        "Africa and South Asia.[14] It is a co-official language of the United Nations, " +
//                        "the European Union and many other world and regional international organisations." +
//                        " It is the most widely spoken Germanic language, accounting for at least 70% of speakers of this Indo-European branch.";


                String mPreLoadedTextOne = "English is largest and language by number of speakers, have" +
                        " on and the most widely spoken Germanic language.";


                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(mPreLoadedTextOne);
                goToTextShowActivity();
            }
        });

        mCardViewPreLoadedTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mPreLoadedTextTwo = "පාසලක් යනු විධිමත්, අවිධිමත් හා නොවිධිමත් අධ්\u200Dයාපනයක් ලබා දී ශිෂ්\u200Dයයා තුළ ප\u200D්\u200Dරජානන, ආවේදන, මනෝචාලක යන ක්\u200Dෂේත\u200D්\u200Dරයන්හි සංවර්ධනය වන දැනුම, ආකල්ප, කුසලතාවන්ගෙන් පරිපූර්ණ වූ මානව හිතවාදී පුරවැසියන් රාශියක් දැයට දායාද කරන ස්ථානය යි. මෙම සාරගර්භ වූ කාර්යභාරය සිදු කිරීම සඳහාත්, පාසල තුළ ඉගෙනුම් ඉගැන්වීම් ක\u200D්\u200Dරියාවලිය සාර්ථක කර ගැනීම සඳහාත් පාසල් පුස්තකාලය විසින් සිදු කරනුයේ අමිල මෙහෙවරකි.\n" +
                        " \n" +
                        " අතීතයේ සාම්ප\u200D්\u200Dරදායික ව පැවති පුස්තකාල වටපිටාවට සාපේක්ෂ ව වර්තමානය වන විට පාසල් පුස්තකාලය සංවර්ධනය වී ඇත. පැරණි පුස්තකාලය ශිෂ්\u200Dයයන්ට පොත් ලබා ගැනීමට හා නැවත භාර දීමට පමණක් සීමා වී තිබුණ ද නූතනය වන විට එම ක\u200D්\u200Dරමය ගතික වී ඇත. එනම් පාසල් පද්ධතිය තුළ අද වන විට දැකිය හැක්කේ ඉගෙනුම් ඉගැන්වීම් ක\u200D්\u200Dරියාවලිය සිදු කරන ඉගෙනුම් සම්පත් මධ්\u200Dයස්ථාන වේ. අධ්\u200Dයාපන සංවර්ධන මධ්\u200Dයස්ථානයක් ලෙස පාසල් ගුරුවරුන්ට මූලාශ\u200D්\u200Dර සැපයීම මඟින් ඉගෙනුම් ඉගැන්වීම් කාර්යය සඳහා පූර්ණ සහයෝගය පුස්තකාලය විසින් සපයනු ලබයි. සමස්ත විෂය මාලාව මනා සේ ක\u200D්\u200Dරියාත්මක කිරීමට සහයෝගය දැක්වීම මෙන් ම ශිෂ්\u200Dය පාඨකයන්ට කියවීමට, ඇහුම්කන් දීමට, සිතීමට හා තනි ව ඉගෙනීමට ඉඩදෙන ස්ථානයක් ලෙස ද අගය කළ යුතු ය. ";


                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(mPreLoadedTextTwo);
                goToTextShowActivity();
            }
        });

        mCardViewPreLoadedTextThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mPreLoadedTextThree = "இந்தியப் பொதுத் தேர்தலில் நரேந்திர மோதி (படம்) தலைமையிலான பாரதிய ஜனதா கட்சியும் அதன் கூட்டணியும் பெரும்பான்மையைப் பெற்று ஆட்சியைத் தக்க வைத்துக் கொண்டது.\n" +
                        "இலங்கையில் தலைநகர் கொழும்பு உள்ளிட்ட 3 நகரங்களில் உயிர்ப்பு ஞாயிறு அன்று இடம்பெற்ற தற்கொலைக் குண்டுவெடிப்புகளில் 46 வெளிநாட்டவர்கள் உட்படக் குறைந்தது 253 பேர் கொல்லப்பட்டனர். 500 பேர் காயமடைந்தனர்.\n" +
                        "சூடானை கிட்டத்தட்ட 30 ஆண்டு காலம் ஆட்சி செய்த அரசுத்தலைவர் உமர் அல்-பசீர் இராணுவப் புரட்சி மூலம் பதவியில் இருந்து அகற்றப்பட்டார்.\n" +
                        "ஏழாண்டுகளாக இலண்டன், எக்குவடோர் தூதரகத்தில் தஞ்சம் அடைந்திருந்த விக்கிலீக்சு நிறுவனர் யூலியன் அசாஞ்சு பிரித்தானியக் காவல்துறையினரால் கைது செய்யப்பட்டார்.\n" +
                        "நியூசிலாந்து, கிறைஸ்ட்சேர்ச் நகரில் இரண்டு பள்ளிவாசல்களில் மேற்கொள்ளப்பட்ட துப்பாக்கிச் சூட்டில் குறைந்தது 50 பேர் கொல்லப்பட்டனர், பலர் படுகாயமடைந்தனர்.";


                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(mPreLoadedTextThree);
                goToTextShowActivity();
            }
        });

        mCardViewPreLoadedTextFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mPreLoadedTextFour = "Malgré son caractère historique remarquable, la notoriété du site de Sant’Omobono ne dépasse pas le milieu scientifique des archéologues, et demeure l’objet de questions et de débats sur sa chronologie complexe et ses interprétations historiques. Le Sant’Omobono Project, lancé en 2009 par les universités de Calabre, du Michigan et la Surintendance des biens culturels de Rome, s’est donné comme objectif de collationner, d’approfondir et de publier les connaissances de l'aire de Sant'Omobono. Dans le même temps, la Surintendance organise de rares visites guidées du site pour de petits groupes d’amateurs.";

                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(mPreLoadedTextFour);
                goToTextShowActivity();
            }
        });

        mCardViewPreLoadedTextFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPreLoadedTextFive = "People celebrate World Environment Day (WED) in many different ways all over the world: planting trees, " +
                        "cleaning up local beaches, organising meetings, joining online protests. Each year the United Nations Environment Programme (UNEP) chooses a particular issue to focus on." +
                        " One year it might be forests, another year it might be wildlife. And each year there is a new host; a city which is the centre point for all the celebrations.";

                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(mPreLoadedTextFive);
                goToTextShowActivity();
            }
        });

        try {
            Analytics.getInstance(this).pushToAnalytics("Pre Loaded Templates Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToTextShowActivity() {
        Intent intent = new Intent(getBaseContext(), TextFromSourceShowActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TextSourcesActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
