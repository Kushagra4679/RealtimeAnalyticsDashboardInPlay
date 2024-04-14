from elasticsearch import Elasticsearch

# Initialize Elasticsearch client
es = Elasticsearch(['https://localhost:9200'], basic_auth=('elastic', '123456'), verify_certs=False)

# Define the index name where your data is ingested
index_name = 'user_events'

# Define the search query (match all documents)
search_query = {
    "query": {
        "match_all": {}
    }
}

# Perform the search query
search_results = es.search(index=index_name, body=search_query)

# Extract and print the search results
total_hits = search_results['hits']['total']['value']
print(f"Total documents found in index '{index_name}': {total_hits}")

# Print the details of each document
for hit in search_results['hits']['hits']:
    print(hit['_source'])  # Print the document source (JSON format)
