/*
 * Copyright 2005-2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.fabric8.spring.boot;

import io.fabric8.kubernetes.api.Kubernetes;
import io.fabric8.kubernetes.api.KubernetesFactory;
import io.fabric8.spring.boot.annotations.ConditionalOnKubernetesAvailable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@ConditionalOnClass({Kubernetes.class})
@EnableConfigurationProperties(KubernetesProperties.class)
public class KubernetesAutoconfigure {

    private static final Logger LOGGER = LoggerFactory.getLogger(KubernetesAutoconfigure.class);

    @Inject
    private KubernetesProperties kubernetesProperties;

    @Bean(name = "kubernetes")
    @ConditionalOnMissingBean
    @ConditionalOnClass(Kubernetes.class)
    public Kubernetes kubernetes() {
        LOGGER.debug("Trying to init {} by auto-configuration.", Kubernetes.class.getSimpleName());
        KubernetesFactory factory = new KubernetesFactory(kubernetesProperties.getKubernetesMasterUrl());
        return factory.createKubernetes();
    }

}
