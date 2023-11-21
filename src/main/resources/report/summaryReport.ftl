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
        thead th{text-align:center;}
        tbody tr:nth-child(even) {background: #CCC}
        tbody  tr:nth-child(odd) {background: #FFF}
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

    </style>
</head>
<body>
<h1>${title}</h1>

<p class="current">Current Version <b>${versionDate}</b> executed on <b>${currentVersion.createdOn}</b> in <b>${currentVersion.duration}</b></p>
<#if previousVersion??>
<p>Previous Version <b>${previousVersion.name}</b> executed on ${previousVersion.createdOn} in <b>${previousVersion.duration}</b></p>
</#if>
<#if currentVersion.otherEnvironments?size &gt; 0>
<p>Execution on same Version <b>${currentVersion.name}</b> but with different Environment <#list currentVersion.otherEnvironments as env>
<b>${env.browser}</b> - <b>${env.database}</b>,&nbsp;
</#list></p>
</#if>
<p><b>Current Environment:</b><ul>
<li>Browser: <b>${currentVersion.currentEnvironment.browser}</b></li>
<li>OS Serveur: <b>${currentVersion.currentEnvironment.os}</b></li>
<#if currentVersion.currentEnvironment.database?has_content>
<li>Database: ${currentVersion.currentEnvironment.database}</li>
</#if>
<li>Tags: ${tags}</li>
</ul>
</p>
<#if reportUrls?size &gt; 0>
    <p>Full Report results are available at: <ul>
    <#list reportUrls as reportUrl>
        <li><a href="${reportUrl.url}" target=_blank>
           <img width="30px" heigth="30px" src="data:image/png;base64, ${reportUrl.base64Img}" />
           ${reportUrl.url}</a></li>
    </#list>
    </ul>
</#if>
<table class="status"><tr><td style="width:20%">
<img src="data:image/png;base64, ${piechart}" alt="Status Chart" />
</td><td><table class="center">
    <thead>
    <tr>
        <th></th>
        <#if previousVersion??> <th>${previousVersion.name}</th></#if>
        <th>${currentVersion.name}</th>
        <#if previousVersion??><th>Delta</th></#if>
    </tr>
    </thead>
    <tbody>
  <tr class="duration">
        <td>Duration</td>
        <#if previousVersion??><td>${previousVersion.duration}</td></#if>
        <td>${currentVersion.duration}</td>
        <#if previousVersion??><td>${trdVersionCompare.deltaDuration}</td></#if>
    </tr>
        <tr class="passed">
        <td>Passed</td>
        <#if previousVersion??><td>${previousVersion.passedTcs?size}</td></#if>
        <td>${currentVersion.passedTcs?size}</td>
        <#if previousVersion??><td>${currentVersion.passedTcs?size - previousVersion.passedTcs?size}</td></#if>
    </tr>
    <tr class="failed">
        <td>Failed</td>
        <#if previousVersion??><td>${previousVersion.failedTcs?size}</td></#if>
        <td>${currentVersion.failedTcs?size}</td>
        <#if previousVersion??><td>${currentVersion.failedTcs?size - previousVersion.failedTcs?size}</td></#if>
    </tr>
    <tr>
        <td>Total Executed</td>
        <#if previousVersion??><td>${previousVersion.passedTcs?size+previousVersion.failedTcs?size}</td></#if>
        <td>${currentVersion.passedTcs?size+currentVersion.failedTcs?size}</td>
        <#if previousVersion??><td>${currentVersion.failedTcs?size + currentVersion.passedTcs?size - previousVersion.failedTcs?size -
            previousVersion.passedTcs?size}
        </td></#if>
    </tr>
    </tbody>
</table>
</td></tr></table>
<#if previousVersion??>
<h2>Status</h2>
<table class="center">
    <tbody>
    <tr>
        <td class="failed">Failed In Both</td>
        <td class="failed">${trdVersionCompare.failedInBoth?size}</td>
        <td class="regression">Regression</td>
        <td class="regression">${trdVersionCompare.regressions?size}</td>
    </tr>
    <tr>
        <td class="passed">Passed In Both</td>
        <td class="passed">${trdVersionCompare.passedInBoth?size}</td>
        <td class="amelioration">Amelioration</td>
        <td class="amelioration">${trdVersionCompare.improvements?size}</td>
    </tr>
    <tr>
        <td class="passed">New Passed</td>
        <td class="passed">${trdVersionCompare.newPassedTcs?size}</td>
        <td class="failed">New Failed</td>
        <td class="failed">${trdVersionCompare.newFailedTcs?size}</td>
    </tr>
    <tr>
        <td class="notexecuted">Not Executed Tcs</td>
        <td class="notexecuted">${trdVersionCompare.oldTcs?size}</td>
        <td></td>
        <td></td>
    </tr>
    </tbody>
</table>
</#if>

<#if trdVersionCompare.regressions?size &gt; 0>
<h2 class="regression"> Regression: <span class="nb">${trdVersionCompare.regressions?size}</span></h2>
<table>
    <thead>
    <tr>
        <th>status</th>
        <th>package</th>
        <th>key</th>
        <th>name</th>
        <th>report</th>
        <th>bugId</th>
        <th>error</th>
         <#if currentVersion.otherEnvironments?size &gt; 0>
             <#list currentVersion.otherEnvironments as env><th class="pEnv">${env.browser}-${env.database}</th></#list>
         </#if>
    </tr>
    </thead>
    <tbody>
    <#list trdVersionCompare.regressions as tc>
    <tr>
        <td>${tc.status}</td>
        <td>${tc.packageName}</td>
        <td class="regression center">${tc.tcKey}</td>
        <td>${tc.name}</td>
        <td class="center"><a href="${tc.reportUrl}" target="_blank">(-R-)</a></td>
        <td>${tc.bugId}</td>
        <td>${tc.errorMessage}</td>
        <#if currentVersion.otherEnvironments?size &gt; 0>
            <#list currentVersion.otherEnvironments as env><td class="pEnv  <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if>"> <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if></td></#list>
        </#if>
    </tr>
    </#list>
    </tbody></table>
</#if>
<#if (trdVersionCompare.failedTcsWithoutBugId?size) &gt; 0>
<h2 class="failed">Failed Testcases Without BugId: <span class="nb">${trdVersionCompare.failedTcsWithoutBugId?size}</span></h2>
<table>
    <thead>
    <tr>
        <th>status</th>
        <th>package</th>
        <th>key</th>
        <th>name</th>
        <th>report</th>
        <th>error</th>
        <#if currentVersion.otherEnvironments?size &gt; 0>
            <#list currentVersion.otherEnvironments as env><th class="pEnv">${env.browser}-${env.database}</th></#list>
        </#if>
    </tr>
    </thead>
    <tbody>
    <#list trdVersionCompare.failedTcsWithoutBugId as tc>
    <tr>
        <td>${tc.status}</td>
        <td>${tc.packageName}</td>
        <td class="failed center">${tc.tcKey}</td>
        <td>${tc.name}</td>
        <td class="center"><a href="${tc.reportUrl}" target="_blank">(-R-)</a></td>
        <td>${tc.errorMessage}</td>
        <#if currentVersion.otherEnvironments?size &gt; 0>
            <#list currentVersion.otherEnvironments as env><td class="pEnv <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if>"> <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if></td></#list>
        </#if>
    </tr>
    </#list>
    </tbody></table>
</#if>
<#if (trdVersionCompare.failedTcsWithBugId?size) &gt; 0>
    <h2 class="failed">Failed Testcases With BugId: <span class="nb">${trdVersionCompare.failedTcsWithBugId?size}</span></h2>
    <table>
        <thead>
        <tr>
            <th>status</th>
            <th>package</th>
            <th>key</th>
            <th>name</th>
            <th>report</th>
            <th>bugId</th>
            <th>error</th>
            <#if currentVersion.otherEnvironments?size &gt; 0>
                <#list currentVersion.otherEnvironments as env><th class="pEnv">${env.browser}-${env.database}</th></#list>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list trdVersionCompare.failedTcsWithBugId as tc>
        <tr>
            <td>${tc.status}</td>
            <td>${tc.packageName}</td>
            <td class="failed center">${tc.tcKey}</td>
            <td>${tc.name}</td>
            <td class="center"><a href="${tc.reportUrl}" target="_blank">(-R-)</a></td>
            <td>${tc.bugId}</td>
            <td>${tc.errorMessage}</td>
            <#if currentVersion.otherEnvironments?size &gt; 0>
                 <#list currentVersion.otherEnvironments as env><td class="pEnv <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if>"> <#if tc.otherEnvironmentResults?api.get(env.id)??>${tc.otherEnvironmentResults?api.get(env.id)}</#if></td></#list>
            </#if>
        </tr>
        </#list>
    </tbody></table>
</#if>
<#if (currentVersion.bugIds?size) &gt; 0>
<h2 class="bug"> Existing Bug List: <span class="nb">${currentVersion.bugIds?size}</span></h2>
 <div class="bugList"> <#list currentVersion.bugIds as bug>
  <div>${bug}</div>
   </#list>
   </div>
</#if>
<#if trdVersionCompare.newPassedTcs?size &gt; 0 >
<h2 class="new"> New Passed Testcases: <span class="nb">${trdVersionCompare.newPassedTcs?size}</span></h2>
<table>
    <thead>
    <tr>
        <th>status</th>
        <th>package</th>
        <th>key</th>
        <th>name</th>
        <th>report</th>
     </tr>
    </thead>
    <tbody>
    <#list trdVersionCompare.newPassedTcs as tc>
        <tr>
            <td>${tc.status}</td>
            <td>${tc.packageName}</td>
            <td class="center>${tc.tcKey}</td>
            <td>${tc.name}</td>
            <td class="center"><a href="${tc.reportUrl}" target="_blank">(-R-)</a></td>
            </th>
        </tr>
        </#list>
    </tbody></table>
</#if>

<#if trdVersionCompare.oldTcs?size &gt; 0>
<h2 class="notexecuted"> Not Executed: <span class="nb">${trdVersionCompare.oldTcs?size}</span></h2>
<table>
    <thead>
    <tr>
        <th>status</th>
        <th>package</th>
        <th>key</th>
        <th>name</th>
        <th>report</th>
    </tr>
    </thead>
    <tbody>
    <#list trdVersionCompare.oldTcs as tc>
    <tr>
        <td>${tc.status}</td>
        <td>${tc.packageName}</td>
        <td class="center">${tc.tcKey}</td>
        <td>${tc.name}</td>
        <td class="center"><a href="${tc.reportUrl}" target="_blank">(-R-)</a></td>
    </tr>
    </#list>
    </tbody></table>
</#if>
<#if currentVersion.services?? ><#if currentVersion.services?size &gt; 0>
<div class="services">
    <p class="current">Services</p>
    <#list currentVersion.services as service>
        <div <#if service.isNew()>class="newService"</#if> >${service.name}:${service.version}, </div>
    </#list>
    <p />
    </div>
</#if></#if>
</body>
</html>