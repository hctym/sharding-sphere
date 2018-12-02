/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.orchestration.internal.registry.config.listener;

import io.shardingsphere.orchestration.internal.registry.config.event.DataSourceChangedEvent;
import io.shardingsphere.orchestration.internal.registry.config.node.ConfigurationNode;
import io.shardingsphere.orchestration.internal.registry.listener.PostShardingOrchestrationEventListener;
import io.shardingsphere.orchestration.internal.registry.listener.ShardingOrchestrationEvent;
import io.shardingsphere.orchestration.internal.registry.state.service.DataSourceService;
import io.shardingsphere.orchestration.reg.api.RegistryCenter;
import io.shardingsphere.orchestration.reg.listener.DataChangedEvent;

/**
 * Data source changed listener.
 *
 * @author panjuan
 */
public final class DataSourceChangedListener extends PostShardingOrchestrationEventListener {
    
    private final String shardingSchemaName;
    
    private final DataSourceService dataSourceService;
    
    public DataSourceChangedListener(final String name, final RegistryCenter regCenter, final String shardingSchemaName) {
        super(regCenter, new ConfigurationNode(name).getDataSourcePath(shardingSchemaName));
        this.shardingSchemaName = shardingSchemaName;
        dataSourceService = new DataSourceService(name, regCenter);
    }
    
    @Override
    protected ShardingOrchestrationEvent createOrchestrationEvent(final DataChangedEvent event) {
        return new DataSourceChangedEvent(shardingSchemaName, dataSourceService.getAvailableDataSourceConfigurations(shardingSchemaName));
    }
}
