#!/bin/bash

mvn --batch-mode verify sonar:sonar \
    -Dsonar.host.url=http://sonar-dop.srv.sunvalley \
    -Dsonar.login=dfb99006ee80cc6bc8dcf9d82badaa5a16a3245b \
    -Dsonar.java.binaries=target/sonar \
    -Dsonar.preview.excludePlugins=issueassign,scmstats

if [ $? -eq 0 ]; then
    echo "sonarqube code-analyze over."
fi
