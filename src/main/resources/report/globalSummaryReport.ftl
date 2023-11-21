<html>
<head>
    <title>${title}</title>
    <style>
        body{width: 90%;    margin: 0 auto;}
        h1{width:100%;    text-align: center;}
        table{width:100%; border: solid;}
        .status td{ vertical-align: middle;}
        .status {border:none}
        thead{background-color: #1976D2;color: white;    }
        .center tbody td {text-align:center;}
        .center {text-align:center;}
        img.center {margin-left: auto; margin-right: auto; display: block;}
        thead th{text-align:center;}
        p.current{font-size: larger;}
        .regression, .regression a{background-color: #EF5350;color: white;    }
        .failed, .failed td, .failed a {background-color: #ec407a;color: white;   }
        .notexecuted{background-color: #CCC;color: white;    }
        .passed, .passed td{background-color: #66BB6A;color: white;    }
        .amelioration{background-color: #00FF00;color: white;    }
        .new{background-color: #0000A0;color: white;    }
        .bug{background-color: #fd407a;color: white;    }
        .bugList div{ display: inline-block;margin: 3px;}
         th.pEnv{background-color: #8CBAE8;}
         td.pEnv.FAIL{background-color: F59FBC;font-size: x-small;text-align: center;}
         td.pEnv.PASS{background-color: #B2DDB4;font-size: x-small;text-align: center;}
         td.pEnv.UNEXECUTED{background-color: #bfbfbf;font-size: x-small;text-align: center;}
         .services div{float:left;font-size:smaller;padding-right: 5px;}
         .services div.newService{font-size:inherit;color:green;font-weight: bold;}
         .services p{clear:both;}

        .detailChart tr {height: 320px; text-align:center;}
        .detailChart {border:none}

    </style>
</head>
<body>
<h1>${title}</h1>

<p class="current"><ul>
<li>Application Version: <b>${versionDate}</b></li>
<li>Os: <#list Oss as Os><b>${Os}</b><#sep>, </#list></li>
<li>Database: <#list Databases as Os><b>${Os}</b><#sep>, </#list></li>
<li>Browser: <#list Browsers as Os><b>${Os}</b><#sep>, </#list></li>
</ul></p>
<div width="100%">
  <center><img src="data:image/png;base64, ${globalChart}" alt="Global Result" class="center" /></center>
</div>
<div width="100%">
<h2>Details by Area</h2><hr>
 <table class="detailChart">
 <#list chartsData as chartData>
        <#if chartData?index % 3==0>
            <#if chartData?index &gt; 0></tr></#if>
            <tr>
        </#if>
        <td >
           <!--<div width="100%">Os: <b>${chartData.environment.os}</b></div>
           <div width="100%">Database: <b>${chartData.environment.database}</b></div>
           <div width="100%">Browser: <b>${chartData.environment.browser}</b></div>
          --> <div width="100%">Area: <b>${chartData.functionalArea}</b></div>
                      <center><img src="data:image/png;base64, ${chartData.base64Chart}" alt="Detail Result" class="center" /></center>
           <div width="100%">Reports:
               <#list chartData.extentReportsUrl as reportUrl>
                  <a href="${reportUrl}" title="${reportUrl}" target=_blank>
                  <img src="data:image/png;base64, ${extentReportIcon}" /></a>&nbsp;
              </#list>
              <#list chartData.allureReportsUrl as reportUrl>
                                <a href="${reportUrl}" title="${reportUrl}" target=_blank>
                                <img src="data:image/png;base64, ${allureReportIcon}" /></a>&nbsp;
              </#list>
              <#list chartData.cucumberReportsUrl as reportUrl>
                                             <a href="${reportUrl}" title="${reportUrl}" target=_blank>
                                             <img src="data:image/png;base64, ${cucumberReportIcon}" /></a>&nbsp;
              </#list>
              <#list chartData.xrayReportsUrl as reportUrl>
                              <a href="${reportUrl}" title="${reportUrl}" target=_blank>
                              <img src="data:image/png;base64, ${xrayReportIcon}" /></a>&nbsp;
              </#list>
              </td>
   </#list>
   </tr>
</table>

</div>

</body>
</html>