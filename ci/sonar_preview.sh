#!/bin/bash

mvn --batch-mode verify sonar:sonar \
    -Dsonar.host.url=http://sonar-dop.srv.sunvalley \
    -Dsonar.login=dfb99006ee80cc6bc8dcf9d82badaa5a16a3245b \
    -Dsonar.analysis.mode=preview \
    -Dsonar.java.binaries=target/sonar \
    -Dsonar.gitlab.project_id=$CI_PROJECT_ID \
    -Dsonar.gitlab.commit_sha=$CI_COMMIT_SHA \
    -Dsonar.gitlab.ref_name=$CI_COMMIT_REF_NAME

if [ $? -eq 0 ]; then
    echo "sonarqube code-analyze-preview over."
fi
