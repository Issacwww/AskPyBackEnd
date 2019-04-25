package com.pitt.service.Preprocess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;

class SynonymReplacer {
    private Map<String, String> synonMap;
    SynonymReplacer() {
        String synonyms = "{\"py3k\": \"python3\",\"python3k\": \"python3\",\"python-modules\": \"python-module\",\"python-nose\": \"nose\",\"python-imaging-library\": \"pil\",\"mysqldb\": \"mysql-python\",\"webpy\": \"web.py\",\"pyplot\": \"matplotlib\",\"python-subprocess-module\": \"subprocess\",\"copy-elision\": \"return-value-optimization\",\"pylab\": \"matplotlib\",\"pythonic\": \"python\",\"python-pil\": \"pil\",\"requests\": \"python-requests\",\"python-interpreter\": \"python\",\"idle\": \"python-idle\",\"idle-ide\": \"python-idle\",\"urls.py\": \"django-urls\",\"python-3\": \"python3\",\"python-ctypes\": \"ctypes\",\"scrapy-spider\": \"scrapy\",\"python-shell\": \"python\",\"pdb-python\": \"pdb\",\"python-c-extension\": \"python-c-api\",\"pycaffe\": \"caffe\",\"python-extensions\": \"python-c-api\",\"epd-python\": \"canopy\",\"python-xray\": \"python-xarray\",\"sql-copy\": \"postgresql-copy\",\"py2to3\": \"python-2to3\",\"2to3\": \"python-2to3\",\"pillow\": \"python-imaging-library\",\"python-coverage\": \"coverage.py\",\"ipython-notebook\": \"jupyter-notebook\",\"xarray\": \"python-xarray\",\"pythonnet\": \"python.net\",\"google-python-api\": \"google-api-python-client\",\"py\": \"python\",\"py.test\": \"pytest\",\"py3\": \"python3\",\"gcloud-python\": \"google-cloud-python\",\"pyunit\": \"python-unittest\",\"python-dash\": \"plotly-dash\"}";
        synonMap = new Gson().fromJson(
                synonyms, new TypeToken<HashMap<String, String>>() {}.getType()
        );
    }

    String replace(String query){
        StringBuilder res = new StringBuilder();
        String[] tmp = query.split("\\s");
        for (String s : tmp)
            res.append(synonMap.getOrDefault(s, s)).append(" ");
        return res.toString().trim();
    }
}
