import requests
from bs4 import BeautifulSoup

def fetch_urls(url):
    try:
        response = requests.get(url)
        response.raise_for_status()  # Raise an error for bad responses
        webpage = response.text

        soup = BeautifulSoup(webpage, 'html.parser')
        links = []
        # Find all anchor tags and extract their href attributes
        for a in soup.find_all('a', href=True):
            href = a['href']
            if href.startswith('http://') or href.startswith('https://'):
                links.append(href)
        return links
    except Exception as e:
        print(f"Exception caught: {e}")
        return []

if __name__ == "__main__":  # Corrected _name_ to __name__
    url = "https://www.amazon.in/?tag=googmantxtmob170-21&ascsubtag=_k_EAIaIQobChMIofDg7TsiAMVZ6RmAh0fPSrNEAAYASAAEgJdJvD_BwE_k_"
    urls = fetch_urls(url)
    for u in urls:
        print(u)
    print(f"Total URLs fetched are {len(urls)}")  # Corrected the message
