from googleapiclient.discovery import build
from googlesearch import search
from bs4 import BeautifulSoup
import requests
import sys
import json

def scrape_search_engine(api_key, cse_id, search_term):
    results = []
    page = 1
    start = (page - 1) * 10 + 1
    url = f"https://www.googleapis.com/customsearch/v1?key={api_key}&cx={cse_id}&q={search_term}&start={start}"

    data = requests.get(url).json()
    search_items = data.get("items")
    j = 0

    for i, search_item in enumerate(search_items, start=1):
        link = search_item.get("link")
        results.append(link)
        j += 1

    return results

def remove_suffix(temp):
    while not(temp.endswith(".edu")):
        temp = temp.rstrip(temp[-1])
    return temp

def start_from_scratch(temp, end):
    while not(temp.endswith(end)):
        temp = temp.rstrip(temp[-1])
    return temp

def remove_num(temp, pos):
    if(temp[pos] == "0"):
        temp = temp.replace("0", "")
    elif(temp[pos] == "1"):
        temp = temp.replace("1", "")
    elif(temp[pos] == "2"):
        temp = temp.replace("2", "")
    elif(temp[pos] == "3"):
        temp = temp.replace("3", "")
    elif(temp[pos] == "4"):
        temp = temp.replace("4", "")
    elif(temp[pos] == "5"):
        temp = temp.replace("5", "")
    elif(temp[pos] == "6"):
        temp = temp.replace("6", "")
    elif(temp[pos] == "7"):
        temp = temp.replace("7", "")
    elif(temp[pos] == "8"):
        temp = temp.replace("8", "")
    elif(temp[pos] == "9"):
        temp = temp.replace("9", "")
    return temp

def remove_prefix(temp):
    non_chars = set('()-:')
    num_chars = set('0123456789')
    caps_alphabet = set('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
    chair_word = "Chair"
    contact_word = "Contact"
    head_word = "Head"
    prof_word = "Professor"
    chem_word = "Chemistry"
    email_word = "Email"
    email_word_alt = "E-mail"
    phone_word = "Phone"
    department_word = "Department"
    biochem_word = "Biochemistry"
    engineer_word = "Engineering"
    office_word = "Office"
    t = 0
    if not(temp.find(office_word) == -1):
        temp = temp.replace(office_word, "")
    if not(temp.find(contact_word) == -1):
        temp = temp.replace(contact_word, "")
    if not(temp.find(biochem_word) == -1):
        temp = temp.replace(biochem_word, "")
    if not(temp.find(engineer_word) == - 1):
        temp = temp.replace(engineer_word, "")
    if not(temp.find(chair_word) == -1):
        temp = temp.replace(chair_word, "")
    if not(temp.find(head_word) == -1):
        temp = temp.replace(head_word, "")
    if not(temp.find(prof_word) == -1):
        temp = temp.replace(prof_word, "")
    if not(temp.find(chem_word) == -1):
        temp = temp.replace(chem_word, "")
    if not(temp.find(email_word) == -1):
        temp = temp.replace(email_word, "")
    if not(temp.find(email_word_alt) == -1):
        temp = temp.replace(email_word_alt, "")
    if not(temp.find(phone_word) == -1):
        temp = temp.replace(phone_word, "")
    if not(temp.find(department_word) == -1):
        temp = temp.replace(department_word, "")
    
    if any((c in num_chars) for c in temp):
        while t == 0:
            for i in range(len(temp)):
                char = temp[i]
                if char.isdigit():
                    if i == 0:
                        temp = remove_num(temp, i)
                        break
                    elif temp[i - 1] == "-":
                        temp = remove_num(temp, i)
                        break
                    elif temp[i + 1] == "-":
                        temp = remove_num(temp, i)
                        break
                    elif temp[i - 1] == "(":
                        temp = remove_num(temp, i)
                        break
                    elif temp[i + 1] == ")":
                        temp = remove_num(temp, i)
                        break
                    elif temp[i - 1] == ".":
                        temp = remove_num(temp, i)
                        break
                    elif temp[i + 1] == ".":
                        temp = remove_num(temp, i)
                if i == len(temp) - 1:
                    t += 1
                    break
    while any((d in non_chars) for d in temp):
        if "(" in temp:
            temp = temp.replace("(", "")
        elif ")" in temp:
            temp = temp.replace(")", "")
        elif "-" in temp:
            temp = temp.replace("-", "")
        elif ":" in temp:
            temp = temp.replace(":", "")
    while temp[0] == ".":
        temp = temp[1:]
    return temp

def remove_nonsense(text):
    if not(text.find("Chemistry") == -1):
        text = text.replace("Chemistry", "")
    if not(text.find("Department") == -1):
        text = text.replace("Department", "")
    if not(text.find("Professor") == -1):
        text = text.replace("Professor", "")
    if not(text.find("Interim") == -1):
        text = text.replace("Interim", "")
    bad_chars = set(',.:')
    if any((c in bad_chars) for c in text):
        if "," in text:
            text = text.replace(",", "")
        if "." in text:
            text = text.replace(".", "")
        if ":" in text:
            text = text.replace(":", "")
    return text

keyword = "Chair"
keyword_alt = "Head"
words = []
email_count = 0
my_api_key = #the api key here has been redacted for privacy purposes
my_cse_id = #the cse key here has been redacted for privacy purposes
page = 1
start = (page - 1) * 10 + 1
contact_stat = 0

print("> Press 1 to look up a single department")
print("> or press 2 for an entire list\n")

user_format = input()
print(" ")
if user_format == "1":
    print("> You have chosen single department")
    print("> Please input institution name\n")

    institution_name = input()

    print(" ")
    print("> You have chosen " + institution_name)
    print("> Now searching chemistry department information for " + institution_name)
    print("> Please wait...\n")

    search_term = institution_name + " Chemistry Department"
    results = scrape_search_engine(my_api_key, my_cse_id, search_term)
    result_links_final = list(filter(None, results))
    link_to_scrape = ""
    
    for i in range(len(result_links_final)):
        temp_object = result_links_final[i]
        if "faculty" in temp_object:
            link_to_scrape = temp_object
            break

    if link_to_scrape == "":
        link_to_scrape = result_links_final[1]
    
    if link_to_scrape == "":
        print("> Could not find any associated faculty directory")
        print("> Terminating process")
        exit()

    html_directory = requests.get(link_to_scrape)
    soup_directory = BeautifulSoup(html_directory.text, 'lxml')

    for script in soup_directory(["script", "style"]):
        script.extract()

    text_directory = soup_directory.get_text()
    lines_directory = (line.strip() for line in text_directory.splitlines())
    chunks_directory = (phrase.strip() for line in lines_directory for phrase in line.split("  "))
    text_directory = '\n'.join(chunk for chunk in chunks_directory if chunk)

    if keyword in text_directory or keyword.upper() in text_directory or keyword_alt in text_directory or keyword_alt.upper() in text_directory:
        print("> Keyword chair or head found")
        print("> Please wait while we pull up relevant information...\n")
    else:
        print("> Neither keyword chair or head was found")
        print("> Terminating process")
        exit()
    
    splits = str.split(text_directory)
    for split in splits:
        if "@" in split or split == "[at]":
            email_count += 1
        words.append(split)

    chair_count = 0
    chair_indices = []

    for i in range(len(words)):
        if keyword in words[i] or keyword.upper() in words[i] or keyword_alt in words[i] or keyword_alt.upper() in words[i]:
            if words[i] == "Header":
                continue
            chair_count += 1
            chair_indices.append(i)

    chair_found = 0
    true_index = -1
    fake_chairs = 0
    temp_stored = 0

    if chair_count > 1:
        for i in range(len(chair_indices)):
            if chair_found == 1:
                break
            if "Associate-" in words[chair_indices[i]]:
                fake_chairs += 1
                continue
            if "Associate" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Associate" in words[chair_indices[i] - 2]:
                fake_chairs += 1
                continue
            if "Vice" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Assistant" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Assistant" in words[chair_indices[i] - 2]: 
                fake_chairs += 1
                continue
            if "Recruiting" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Endowed" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Welcome" in words[chair_indices[i] - 3]:
                fake_chairs += 1
                continue
            if "Welcome" in words[chair_indices[i] + 1]:
                fake_chairs += 1
                continue
            if "Faculty" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Faculty" in words[chair_indices[i] - 6]:
                fake_chairs += 1
                continue
            if "Provostial" in words[chair_indices[i] - 1]:
                fake_chairs += 1
                continue
            if "Biomedical" in words[chair_indices[i] + 2]:
                fake_chairs += 1
                continue
            if "then" in words[chair_indices[i] - 2]:
                fake_chairs += 1
                continue
            if "@" in words[chair_indices[i]]:
                fake_chairs += 1
                continue
            if "FACULTY" in words[chair_indices[i] + 1]:
                fake_chairs += 1
                continue
            if "Faculty" in words[chair_indices[i] + 1]:
                fake_chairs += 1
                continue
            if "Department" in words[chair_indices[i] - 1]:
                chair_found += 1
                true_index = chair_indices[i]
            if "Department" in words[chair_indices[i] + 3]:
                chair_found += 1
                true_index = chair_indices[i]
            if "and" in words[chair_indices[i] - 1]:
                chair_found += 1
                true_index = chair_indices[i]
            if "&" in words[chair_indices[i] - 1]:
                chair_found += 1
                true_index = chair_indices[i]
            if "Interim" in words[chair_indices[i] - 1]:
                chair_found += 1
                true_index = chair_indices[i]
            if chair_count - fake_chairs == 1:
                chair_found += 1
                true_index = chair_indices[i]
            else:
                if temp_stored == 0:
                    temp_stored += 1
                    true_index = chair_indices[i]
                else:
                    if "Chairperson" in words[chair_indices[i]]:
                        true_index = chair_indices[i]
    else:
        true_index = chair_indices[0]

    if email_count > 6:
        i = true_index
        x = 0
        while x < 1:
            if "@" in words[i]:
                temp = words[i]
                temp = remove_suffix(temp)
                temp = remove_prefix(temp)
                print(temp)
                if len(temp) < 2:
                    print("> Could not find email, please check faculty URL")
                    print("> URL: " + link_to_scrape)
                    print("> Terminating sequence...\n")
                    exit()
                x += 1
                break
            elif words[i] == "[at]":
                pre = remove_prefix(words[i - 1])
                suf = remove_suffix(words[i + 1])
                print(pre, "@", suf)
                x += 1
                break
            i += 1
    else:
        site_links = []
        
        for link in soup_directory.find_all('a'):
            site_links.append(link.get('href'))
        
        url_final = list(filter(None, site_links))
        stop = 0
        real_url = " "
        j = true_index

        while stop < 1:
            if words[j] == "to":
                j -= 1
                continue
            if words[j] == "of":
                j -= 1
                continue
            if words[j] == "and":
                j -= 1
                continue
            if words[j] == "for":
                j -= 1
                continue
            if words[j] == "in":
                j -= 1
                continue
            if words[j] == "bio":
                j -= 1
                continue
            if words[j] == "School":
                j -= 1
                continue
            if words[j] == "Department":
                j -= 1
                continue
            if words[j] == "Chair":
                j -= 1
                continue
            if words[j] == "Grant":
                j -= 1
                continue
            if words[j] == "Arts":
                j -= 1
                continue
            if words[j] == "Title":
                j -= 1
                continue

            text = words[j]
            text = remove_nonsense(text)

            if len(text) < 2:
                j -= 1
                continue

            for l in range(len(url_final)):
                temp_object = url_final[l]
                if ".jpg" in temp_object:
                    continue
                elif text in temp_object or text.lower() in temp_object:
                    stop = 1
                    real_url = temp_object
                    break
            
            j -= 1
        
        try:
            chair_page = requests.get(real_url)
            print(real_url)
        except requests.exceptions.MissingSchema as errh:
            institution_page = start_from_scratch(link_to_scrape, ".edu")
            institution_page = institution_page + real_url
            real_url = institution_page
            print(institution_page)

        chair_page = requests.get(real_url)
        second_soup = BeautifulSoup(chair_page.text, 'html.parser')
        def_chars = set('@')

        for second_script in second_soup(["script", "style"]):
            second_script.extract()

        second_text = second_soup.get_text()
        second_lines = (second_line.strip() for second_line in second_text.splitlines())
        second_chunks = (second_phrase.strip() for second_line in second_lines for second_phrase in second_line.split("  "))
        second_text = '\n'.join(second_chunk for second_chunk in second_chunks if second_chunk)
        n = 0

        if keyword in second_text or keyword.upper() in second_text or keyword_alt in second_text or keyword_alt.upper() in second_text:
            splits = str.split(second_text)
            for split in splits:
                if n == 1:
                    break
                if any((c in def_chars) for c in split):
                    if len(split) < 2:
                        continue
                    elif not(".edu" in split):
                        continue
                    else:
                        temp_object = split
                        temp_object = remove_prefix(temp_object)
                        temp_object = remove_suffix(temp_object)
                        print(temp_object)

                        if len(temp_object) < 1:
                            print("> Could not find email, please check faculty URL")
                            print("> URL: " + link_to_scrape)
                            print("> Terminating sequence...\n")

                        n += 1
        else:
            print("> Neither keyword chair or head was found")
            print("> Terminating process\n")
            exit()
else:
    print("> You have chosen institution list")
    print("> Please type in the name of the associated text file\n")

    txt_file = input()
    file_object = open(txt_file, "r")
    uni_list = []

    while(True):
        line = file_object.readline()

        if not line:
            break

        uni_list.append(line.strip())
    
    for i in range(len(uni_list)):
        search_term = uni_list[i] + "Chemistry Department"
        results = scrape_search_engine(my_api_key, my_cse_id, search_term)
        result_links_final = list(filter(None, results))
        link_to_scrape = ""

        for j in range(len(result_links_final)):
            temp_object = result_links_final[j]
            if "directory" in temp_object:
                link_to_scrape = temp_object
                break
            if "faculty" in temp_object:
                if "interests" in temp_object:
                    continue
                link_to_scrape = temp_object
                break
            if "people" in temp_object:
                link_to_scrape = temp_object
                break

        
        if link_to_scrape == "":
            print("> Could not find any associated faculty directory for " + uni_list[i])
            print(" ")
            continue

        html_directory = requests.get(link_to_scrape)
        soup_directory = BeautifulSoup(html_directory.text, 'lxml')

        for script in soup_directory(["script", "style"]):
            script.extract()

        text_directory = soup_directory.get_text()
        lines_directory = (line.strip() for line in text_directory.splitlines())
        chunks_directory = (phrase.strip() for line in lines_directory for phrase in line.split("  "))
        text_directory = '\n'.join(chunk for chunk in chunks_directory if chunk)

        if keyword in text_directory or keyword.upper() in text_directory or keyword_alt in text_directory or keyword_alt.upper() in text_directory:
            splits = str.split(text_directory)
            
            for split in splits:
                if "@" in split or split == "[at]":
                    email_count += 1
                words.append(split)
        else:
            print("> Neither keyword chair or head was found in faculty URL for " + uni_list[i])
            print(" ")
            continue

        chair_count = 0
        chair_indices = []

        for j in range(len(words)):
            if keyword in words[j] or keyword.upper() in words[j] or keyword_alt in words[j] or keyword_alt.upper() in words[j]:
                if words[j] == "Header":
                    continue
                chair_count += 1
                chair_indices.append(j)
        
        chair_found = 0
        true_index = -1
        fake_chairs = 0
        temp_stored = 0

        if chair_count > 1:
            for j in range(len(chair_indices)):
                if chair_found == 1:
                    break
                if "Associate-" in words[chair_indices[j]]:
                    fake_chairs += 1
                    continue
                if "Associate" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Associate" in words[chair_indices[j] - 2]:
                    fake_chairs += 1
                    continue
                if "Vice" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Assistant" in words[chair_indices[j] - 2]:
                    fake_chairs += 1
                    continue
                if "Recruiting" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Endowed" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Welcome" in words[chair_indices[j] - 3]:
                    fake_chairs += 1
                    continue
                if "Welcome" in words[chair_indices[j] + 1]:
                    fake_chairs += 1
                    continue
                if "Faculty" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Faculty" in words[chair_indices[j] - 6]:
                    fake_chairs += 1
                    continue
                if "Provostial" in words[chair_indices[j] - 1]:
                    fake_chairs += 1
                    continue
                if "Biomedical" in words[chair_indices[j] + 2]:
                    fake_chairs += 1
                    continue
                if "then" in words[chair_indices[j] - 2]:
                    fake_chairs += 1
                    continue
                if "@" in words[chair_indices[j]]:
                    fake_chairs += 1
                    continue
                if "FACULTY" in words[chair_indices[j] + 1]:
                    fake_chairs += 1
                    continue
                if "Faculty" in words[chair_indices[j] + 1]:
                    fake_chairs += 1
                    continue
                if "Department" in words[chair_indices[j] - 1]:
                    chair_found += 1
                    true_index = chair_indices[j]
                if "Department" in words[chair_indices[j] + 3]:
                    chair_found += 1
                    true_index = chair_indices[j]
                if "and" in words[chair_indices[j] - 1]:
                    chair_found += 1
                    true_index = chair_indices[j]
                if "&" in words[chair_indices[j] - 1]:
                    chair_found += 1
                    true_index = chair_indices[j]
                if "Interim" in words[chair_indices[j] - 1]:
                    chair_found += 1
                    true_index = chair_indices[j]
                if chair_count - fake_chairs == 1:
                    chair_found += 1
                    true_index = chair_indices[j]
                else:
                    if temp_stored == 0:
                        temp_stored += 1
                        true_index = chair_indices[j]
                    else:
                        if "Chairperson" in words[chair_indices[j]]:
                            true_index = chair_indices[j]
        else:
            true_index = chair_indices[0]
        
        if email_count > 6:
            j = true_index
            x = 0
            while x < 1:
                if "@" in words[j]:
                    temp = words[j]
                    temp = remove_suffix(temp)
                    temp = remove_prefix(temp)
                    print(temp)
                    
                    if len(temp) < 2:
                        print("> Could not find email for " + uni_list[i] + ", please check faculty URL")
                        print("> URL: " + link_to_scrape)
                        print(" ")

                    print(" ")

                    x += 1
                    break
                elif words[j] == "[at]":
                    pre = remove_prefix(words[i - 1])
                    suf = remove_suffix(words[j + 1])
                    print(pre, "@", suf)
                    print(" ")

                    x += 1
                    break
                j += 1
        else:
            site_links = []

            for link in soup_directory.find_all('a'):
                site_links.append(link.get('href'))

            url_final = list(filter(None, site_links))
            stop = 0
            real_url = " "
            k = true_index

            while stop < 1:
                if words[k] == "to":
                    j -= 1
                    continue
                if words[k] == "of":
                    j -= 1
                    continue
                if words[k] == "and":
                    j -= 1
                    continue
                if words[k] == "for":
                    j -= 1
                    continue
                if words[k] == "in":
                    j -= 1
                    continue
                if words[k] == "bio":
                    j -= 1
                    continue
                if words[k] == "School":
                    j -= 1
                    continue
                if words[k] == "Department":
                    j -= 1
                    continue
                if words[k] == "Chair":
                    j -= 1
                    continue
                if words[k] == "Grant":
                    j -= 1
                    continue
                if words[k] == "Arts":
                    j -= 1
                    continue
                if words[k] == "Title":
                    j -= 1
                    continue

                text = words[j]
                text = remove_nonsense(text)

                if len(text) < 2:
                    k -= 1
                    continue

                for l in range(len(url_final)):
                    temp_object = url_final[l]
                    if ".jpg" in temp_object:
                        continue
                    elif text in temp_object or text.lower() in temp_object:
                        stop = 1
                        real_url = temp_object
                        break
                
                k -= 1
            
            try:
                chair_page = requests.get(real_url)
                print(real_url)
            except requests.exception.MissingSchema as errh:
                institution_page = start_from_scratch(link_to_scrape, ".edu")
                institution_page = institution_page + real_url
                real_url = institution_page
                print(institution_page)
            
            chair_page = requests.get(real_url)
            second_soup = BeautifulSoup(chair_page.text, 'html.parser')
            def_chars = set('@')

            for second_script in second_soup(["script", "style"]):
                second_script.extract()
            
            second_text = second_soup.get_text()
            second_lines = (second_line.strip() for second_line in second_text.splitlines())
            second_chunks = (second_phrase.strip() for second_line in second_lines for second_phrase in second_line.split("  "))
            second_text = '\n'.join(second_chunk for second_chunk in second_chunks if second_chunk)
            n = 0
            
            if keyword in second_text or keyword.upper() in second_text or keyword_alt in second_text or keyword_alt.upper() in second_text:
                splits = str.split(second_text)
                for split in splits:
                    if n == 1:
                        break
                    if any((c in def_chars) for c in split):
                        if len(split) < 2:
                            continue
                        elif not(".edu" in split):
                            continue
                        else:
                            temp_object = split
                            temp_object = remove_prefix(temp_object)
                            temp_object = remove_suffix(temp_object)
                            print(temp_object)

                            if len(temp_object) < 1:
                                print("> Could not find email for " + uni_list[i])
                                print("> URL: " + real_url)
                                print(" ")

                            n += 1
            else:
                print("> Neither keyword chair or head was found for " + uni_list[i])
                print("> Please look up " + uni_list[i] + " Chemistry Department\n")

    file_object.close()
