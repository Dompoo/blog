#!/usr/bin/env python3
import os
import re
from pathlib import Path
from urllib.parse import unquote

# 경로 설정
base_dir = Path('/Users/dompoo/obsidian_vault/content')
기타_dir = base_dir / '기타'
첨부파일_dir = base_dir / '_첨부파일'

# _첨부파일 디렉토리의 모든 파일 목록 가져오기
available_images = {}
for img_file in 첨부파일_dir.iterdir():
    if img_file.is_file():
        available_images[img_file.name] = img_file.name

print(f"_첨부파일 디렉토리에서 {len(available_images)}개 이미지 파일 발견")
print()

# 마크다운 파일 처리
md_files = list(기타_dir.glob('*.md'))
total_updated = 0
total_links_fixed = 0

for md_file in md_files:
    with open(md_file, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content
    links_fixed_in_file = 0

    # 이미지 링크 패턴: ![...](경로)
    def replace_image_link(match):
        nonlocal links_fixed_in_file
        alt_text = match.group(1)
        img_path = match.group(2)

        # 이미 올바른 경로인 경우 건너뛰기
        if img_path.startswith('../_첨부파일/'):
            return match.group(0)

        # URL 디코딩
        decoded_path = unquote(img_path)

        # 경로에서 파일명 추출
        if '/' in decoded_path:
            parts = decoded_path.split('/')
            original_filename = parts[-1]
        else:
            original_filename = decoded_path

        # 파일명에서 노션 ID가 포함된 디렉토리명 부분 추출
        if '/' in decoded_path:
            dir_part = decoded_path.split('/')[0]
            # 노션 ID 제거
            clean_dir_name = re.sub(r'\s+[0-9a-f]{32}$', '', dir_part)

            # _첨부파일에서 매칭되는 파일 찾기
            # 패턴: {clean_dir_name}_{original_filename}
            expected_filename = f"{clean_dir_name}_{original_filename}"

            if expected_filename in available_images:
                links_fixed_in_file += 1
                return f'![{alt_text}](../_첨부파일/{expected_filename})'

        # 매칭되는 파일을 못 찾은 경우, 원본 파일명으로 시도
        if original_filename in available_images:
            links_fixed_in_file += 1
            return f'![{alt_text}](../_첨부파일/{original_filename})'

        # 그래도 못 찾으면 경고 출력하고 원본 유지
        print(f"경고: {md_file.name}에서 '{img_path}' 파일을 찾을 수 없음")
        return match.group(0)

    # 이미지 링크 교체
    content = re.sub(r'!\[(.*?)\]\((.*?)\)', replace_image_link, content)

    if content != original_content:
        with open(md_file, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"✓ {md_file.name}: {links_fixed_in_file}개 링크 수정")
        total_updated += 1
        total_links_fixed += links_fixed_in_file

print()
print("=" * 60)
print(f"완료: {total_updated}개 파일에서 총 {total_links_fixed}개 이미지 링크 수정")
print("=" * 60)
